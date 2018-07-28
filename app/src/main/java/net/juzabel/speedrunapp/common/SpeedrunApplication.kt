package net.juzabel.speedrunapp.common

import android.app.Application
import net.juzabel.speedrunapp.di.components.ApplicationComponent
import net.juzabel.speedrunapp.di.components.DaggerApplicationComponent
import net.juzabel.speedrunapp.di.modules.ApplicationModule

class SpeedrunApplication : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectApplicationComponent()
    }

    private fun injectApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        applicationComponent.inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent = applicationComponent
}