package net.juzabel.speedrunapp.di.components

import dagger.Subcomponent
import net.juzabel.speedrunapp.di.modules.ActivityModule
import net.juzabel.speedrunapp.di.modules.FragmentModule
import net.juzabel.speedrunapp.presentation.MainActivity

@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)

    fun plusFragmentComponent(fragmentModule: FragmentModule): FragmentComponent
}