package net.juzabel.speedrunapp.di.modules

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ActivityModule @Inject constructor(private val activity: AppCompatActivity) {

    @Provides
    fun provideActivity(): AppCompatActivity = activity

}