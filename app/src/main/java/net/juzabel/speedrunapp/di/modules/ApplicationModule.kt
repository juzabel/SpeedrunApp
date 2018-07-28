package net.juzabel.speedrunapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import net.juzabel.speedrunapp.common.SpeedrunApplication

@Module
class ApplicationModule(private val speedrunApplication: SpeedrunApplication) {

    @Provides
    fun provideSpeedrunApplication(): SpeedrunApplication  = speedrunApplication

    @Provides
    fun provideContext(): Context = speedrunApplication

}