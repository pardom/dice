package dev.pardo.dice.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.seismic.ShakeDetector
import dev.pardo.dice.App
import dev.pardo.dice.Drawables
import dev.pardo.dice.R
import dev.pardo.dice.app.Dice
import oolong.Dispatch
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : Activity(), ShakeDetector.Listener {

    private val containerView by lazy(NONE) { findViewById<View>(R.id.container_view) }
    private val historyRecyclerView by lazy(NONE) { findViewById<RecyclerView>(R.id.history_recycler_view) }
    private val helpTextView by lazy(NONE) { findViewById<TextView>(R.id.help_text_view) }
    private val faceImageView by lazy(NONE) { findViewById<ImageView>(R.id.face_image_view) }

    private val historyAdapter = HistoryAdapter()

    private var onShake: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val shakeDetector = ShakeDetector(this)
        shakeDetector.start(sensorManager)

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

        onShake = {
            dispatch(props.onUserShookDevice())
        }

        historyAdapter.items = props.rolls.dropLast(1)

        val face = props.rolls.lastOrNull()
        if (face != null) {
            helpTextView.visibility = View.GONE
            faceImageView.visibility = View.VISIBLE

            faceImageView.animate()
                .rotation(180F)
                .setUpdateListener { animator ->
                    if (animator.animatedFraction >= 0.5F) {
                        faceImageView.setImageResource(Drawables.DIE_FACES[face - 1])
                    }
                }
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationCancel(animation: Animator?) {
                        faceImageView.rotation = 0F
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        faceImageView.rotation = 0F
                    }
                })

        } else {
            helpTextView.visibility = View.VISIBLE
            faceImageView.visibility = View.GONE
        }
    }

    override fun hearShake() {
        onShake()
    }

}
