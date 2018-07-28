package net.juzabel.speedrunapp.di.components

import dagger.Subcomponent
import net.juzabel.speedrunapp.di.modules.FragmentModule
import net.juzabel.speedrunapp.presentation.view.fragment.HomeFragment

@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    fun inject(homeFragment: HomeFragment)
}