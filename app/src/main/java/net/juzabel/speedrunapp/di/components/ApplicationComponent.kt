package net.juzabel.speedrunapp.di.components

import dagger.Component
import net.juzabel.speedrunapp.common.SpeedrunApplication
import net.juzabel.speedrunapp.di.modules.ActivityModule
import net.juzabel.speedrunapp.di.modules.ApplicationModule

@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(speedrunApplication: SpeedrunApplication)

    fun plusActivityComponent(activityModule: ActivityModule) : ActivityComponent

}