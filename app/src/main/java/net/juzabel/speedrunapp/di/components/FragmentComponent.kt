package net.juzabel.speedrunapp.di.components

import dagger.Subcomponent
import net.juzabel.speedrunapp.di.modules.FragmentModule
import net.juzabel.speedrunapp.presentation.view.fragment.HomeFragment
import net.juzabel.speedrunapp.presentation.view.fragment.RunFragment

@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(runFragment: RunFragment)
}