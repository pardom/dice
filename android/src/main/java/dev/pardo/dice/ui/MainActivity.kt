package dev.pardo.dice.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.pardo.dice.App
import dev.pardo.dice.Drawables
import dev.pardo.dice.R
import dev.pardo.dice.app.Dice
import oolong.Dispatch
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.random.Random

class MainActivity : Activity() {

    private val containerView by lazy(NONE) { findViewById<View>(R.id.container_view) }
    private val historyRecyclerView by lazy(NONE) { findViewById<RecyclerView>(R.id.history_recycler_view) }
    private val helpTextView by lazy(NONE) { findViewById<TextView>(R.id.help_text_view) }
    private val faceImageView by lazy(NONE) { findViewById<ImageView>(R.id.face_image_view) }

    private val historyAdapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        historyRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        historyRecyclerView.itemAnimator = HistoryItemAnimator()
        historyRecyclerView.adapter = historyAdapter
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        App.setRender(::render)
    }

    override fun onDetachedFromWindow() {
        App.clearRender()
        super.onDetachedFromWindow()
    }

    private fun render(props: Dice.Props, dispatch: Dispatch<Dice.Msg>) {
        containerView.setOnClickListener {
            dispatch(props.onUserClickedRollButton())
        }

        historyAdapter.items = props.rolls.dropLast(1)

        val face = props.rolls.lastOrNull()
        if (face != null) {
            helpTextView.visibility = View.GONE
            faceImageView.visibility = View.VISIBLE

            val rotation = Random.nextInt(360, 720).toFloat()
            val maxTranslation = (containerView.width / 4)
            val translationX = Random.nextInt(-maxTranslation, maxTranslation).toFloat()
            val translationY = Random.nextInt(-maxTranslation, maxTranslation).toFloat()

            val rotations = (rotation / 360).toInt()
            val faces = (0..rotations * 2).map { it.rem(6) + 1 }.shuffled() + face

            faceImageView.animate()
                .setDuration(1000)
                .rotationBy(rotation)
                .translationX(translationX)
                .translationY(translationY)
                .setUpdateListener { animator ->
                    val index = (faces.size * animator.animatedFraction).toInt().coerceAtMost(faces.size - 1)
                    val nextFace = faces[index]
                    faceImageView.setImageResource(Drawables.DIE_FACES[nextFace - 1])
                }
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        faceImageView.setImageResource(Drawables.DIE_FACES[face - 1])
                    }
                })
                .start()

        } else {
            helpTextView.visibility = View.VISIBLE
            faceImageView.visibility = View.GONE
        }
    }

}
