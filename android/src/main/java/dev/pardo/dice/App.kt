package dev.pardo.dice

import android.app.Application
import dev.pardo.dice.app.Dice
import oolong.Oolong
import oolong.Render

class App : Application() {

    private var renderProxy = RenderProxy<Dice.Msg, Dice.Props>()

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        val inject = Inject(applicationContext)
        val dispose = Oolong.runtime(
            inject.init,
            inject.update,
            Dice.view,
            renderProxy.render
        )
    }

    companion object {

        private lateinit var INSTANCE: App

        fun setRender(render: Render<Dice.Msg, Dice.Props>) {
            INSTANCE.renderProxy.setDelegate(render)
        }

        fun clearRender() {
            INSTANCE.renderProxy.clearDelegate()
        }

    }

}
