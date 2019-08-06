package dev.pardo.dice.ui

import android.app.Activity
import android.hardware.SensorManager
import android.os.Bundle
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.squareup.seismic.ShakeDetector
import dev.pardo.dice.App
import dev.pardo.dice.R
import dev.pardo.dice.app.Dice
import oolong.Dispatch
import kotlin.LazyThreadSafetyMode.NONE

class AppActivity : Activity(), ShakeDetector.Listener {

    private val lithoView by lazy(NONE) { findViewById<LithoView>(R.id.litho_view) }
    private val componentContext by lazy { ComponentContext(this) }

    private var onShake: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val shakeDetector = ShakeDetector(this)
        shakeDetector.start(sensorManager)
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

    override fun hearShake() {
        onShake()
    }

}
