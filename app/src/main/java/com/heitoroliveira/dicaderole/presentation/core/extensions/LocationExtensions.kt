package com.heitoroliveira.dicaderole.presentation.core.extensions

import android.app.Activity
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.widget.Toast

fun Location.navigateToThere(activity: Activity) {
    val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
        `package` = "com.google.android.apps.maps"
    }
    if (mapIntent.resolveActivity(activity.packageManager) != null) {
        activity.startActivity(mapIntent)
    } else {
        Toast
            .makeText(activity, "Não foi possível navegar até essa localização", Toast.LENGTH_LONG)
            .show()
    }
}