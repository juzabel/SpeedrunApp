package net.juzabel.speedrunapp.presentation.base.extensions

import android.support.v4.app.Fragment
import net.juzabel.speedrunapp.di.components.ActivityComponent
import net.juzabel.speedrunapp.presentation.MainActivity

object FragmentExtensions {
    fun Fragment.getActivityComponent() : ActivityComponent = (activity as MainActivity).getActivityComponent()
}