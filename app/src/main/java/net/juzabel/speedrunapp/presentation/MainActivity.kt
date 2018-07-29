package net.juzabel.speedrunapp.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.juzabel.speedrunapp.R
import net.juzabel.speedrunapp.common.SpeedrunApplication
import net.juzabel.speedrunapp.di.components.ActivityComponent
import net.juzabel.speedrunapp.di.modules.ActivityModule
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var activityComponent: ActivityComponent

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun injectActivityComponent() {
        activityComponent = getSpeedrunApplication().getApplicationComponent().plusActivityComponent(ActivityModule(this))
        activityComponent.inject(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        injectActivityComponent()
        navigator.loadHomeFragment()
    }

    fun getSpeedrunApplication(): SpeedrunApplication = application as SpeedrunApplication

    fun getActivityComponent(): ActivityComponent = activityComponent

}
