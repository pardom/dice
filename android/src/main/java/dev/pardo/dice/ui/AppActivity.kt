package dev.pardo.dice.ui

import android.app.Activity
import android.os.Bundle
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import dev.pardo.dice.App
import dev.pardo.dice.R
import dev.pardo.dice.app.Dice
import oolong.Dispatch
import kotlin.LazyThreadSafetyMode.NONE

class AppActivity : Activity() {

    private val lithoView by lazy(NONE) { findViewById<LithoView>(R.id.litho_view) }
    private val componentContext by lazy { ComponentContext(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
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
        val diceComponent = DiceComponent.create(componentContext)
            .props(props)
            .dispatch(dispatch)
            .build()

        lithoView.setComponentAsync(diceComponent)
    }

}
