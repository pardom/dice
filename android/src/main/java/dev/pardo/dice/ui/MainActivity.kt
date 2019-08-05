package dev.pardo.dice.ui

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
import kotlin.random.nextInt

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
            faceImageView.setImageResource(Drawables.DIE_FACES[face - 1])
            val faces = (1..5).map { Random.nextInt(1..6) } + face

        } else {
            helpTextView.visibility = View.VISIBLE
            faceImageView.visibility = View.GONE
        }
    }

}
