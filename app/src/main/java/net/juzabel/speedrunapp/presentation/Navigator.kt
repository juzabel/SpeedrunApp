package net.juzabel.speedrunapp.presentation

import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import dagger.Lazy
import net.juzabel.speedrunapp.R
import net.juzabel.speedrunapp.presentation.view.fragment.HomeFragment
import net.juzabel.speedrunapp.presentation.view.fragment.RunFragment
import javax.inject.Inject

class Navigator @Inject constructor(private val activity: Lazy<AppCompatActivity>){

    fun loadHomeFragment() {
        activity.get().supportFragmentManager.beginTransaction()
                .replace(R.id.flayFragmentContainer, HomeFragment.newInstance()).commit()
    }
    fun loadRunFragment(gameId: String){
        activity.get().supportFragmentManager.beginTransaction()
                .replace(R.id.flayFragmentContainer, RunFragment.newInstance(gameId))
                .addToBackStack(RunFragment::class.toString()).commit()
    }

    fun showMessage(message: String){
        Toast.makeText(activity.get(), message, Toast.LENGTH_LONG).show()
    }

}