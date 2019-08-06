package dev.pardo.dice

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.litho.LithoFlipperDescriptors
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.litho.config.ComponentsConfiguration
import com.facebook.soloader.SoLoader
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

        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            ComponentsConfiguration.isDebugModeEnabled = true

            val descriptorMapping = DescriptorMapping.withDefaults()
            LithoFlipperDescriptors.add(descriptorMapping)

            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, descriptorMapping))
            client.addPlugin(SharedPreferencesFlipperPlugin(this))
            client.start()
        }

        val inject = Inject(applicationContext)

        Oolong.runtime(
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
