package net.juzabel.speedrunapp.presentation.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import dagger.Lazy
import javax.inject.Inject

class ImageUtil @Inject constructor(private val context: Lazy<Context>){
    fun load(path: String, imageView: ImageView){
        Glide.with(context.get()).load(path).into(imageView)
    }
}