package com.nmt.kmpwallpaper.infrastructure.resolution

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import android.view.WindowMetrics
import androidx.annotation.RequiresApi
import com.nmt.kmpwallpaper.infrastructure.resolution.ScreenResolution.api
import io.github.tunguyen227.PlatformContext

object ScreenResolution {
    val api: Api =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ApiLevel30()
        else Api()


    @Suppress("DEPRECATION")
    open class Api {
        open fun getScreenSize(context: Context): android.util.Size {
            val display = context.getSystemService(WindowManager::class.java).defaultDisplay
            val metrics = if (display != null) {
                DisplayMetrics().also { display.getRealMetrics(it) }
            } else {
                Resources.getSystem().displayMetrics
            }
            return android.util.Size(metrics.widthPixels, metrics.heightPixels)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private class ApiLevel30 : Api() {
        override fun getScreenSize(context: Context): android.util.Size {
            val metrics: WindowMetrics = context.getSystemService(WindowManager::class.java).currentWindowMetrics
            return android.util.Size(metrics.bounds.width(), metrics.bounds.height())
        }
    }
}

actual fun getDeviceSize(context: PlatformContext): Size  {
    val androidSize = api.getScreenSize(context)
    val size = Size
    size.width = androidSize.width
    size.height = androidSize.height
    return size
}