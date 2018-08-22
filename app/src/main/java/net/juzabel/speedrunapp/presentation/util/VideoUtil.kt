package net.juzabel.speedrunapp.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import javax.inject.Inject

class VideoUtil @Inject constructor(private val context: Context) {

    fun loadVideo(uri: String) {
        val videoUri: Uri = Uri.parse(uri)
        val launchIntent: Intent = Intent(Intent.ACTION_VIEW, videoUri)
        val packageManager = context.packageManager
        val activities = packageManager.queryIntentActivities(launchIntent, 0)
        val isIntentSafe = activities.size > 0

        if (isIntentSafe) {
            context.startActivity(launchIntent)
        }
    }
}