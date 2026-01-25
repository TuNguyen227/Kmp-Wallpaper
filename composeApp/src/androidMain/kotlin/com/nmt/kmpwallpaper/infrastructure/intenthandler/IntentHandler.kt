package com.nmt.kmpwallpaper.infrastructure.intenthandler

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

object IntentHandler {
    fun openUri(
        context: Context,
        uri: String,
    ) {
        val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
        context.startActivity(intent)
    }
}
