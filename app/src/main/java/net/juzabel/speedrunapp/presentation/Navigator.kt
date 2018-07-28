package net.juzabel.speedrunapp.presentation

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import dagger.Lazy
import net.juzabel.speedrunapp.presentation.view.fragment.HomeFragment
import javax.inject.Inject

class Navigator @Inject constructor(private val activity: Lazy<AppCompatActivity>){

    fun loadHomeFragment(@IdRes container: Int) {
        activity.get().supportFragmentManager.beginTransaction().replace(container, HomeFragment.newInstance()).commit()
    }

    fun showMessage(message: String){
        Toast.makeText(activity.get(), message, Toast.LENGTH_LONG).show()
    }

}