package com.nmt.kmpwallpaper.infrastructure.wallpaper

import android.content.Context
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.DisplayMetrics
import android.util.Size
import android.view.Display
import android.view.WindowManager
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import coil3.Bitmap
import io.github.tunguyen227.PlatformContext
import java.io.IOException


private typealias AndroidWallpaperManager = android.app.WallpaperManager

actual object WallpaperManager {
    private var manager : AndroidWallpaperManager? = null
    private var screenResolution : Size = Size(0,0)
    actual fun initialize(platformContext: PlatformContext) {
        manager = AndroidWallpaperManager.getInstance(platformContext.applicationContext)
        screenResolution = ScreenResolution.getScreenSize(platformContext.applicationContext)
    }
    actual fun setBitmapAsHomeScreen(bitmap: Bitmap) {
        setAs(bitmap = bitmap, AndroidWallpaperManager.FLAG_SYSTEM)
        bitmap.recycle()
    }

    actual fun setBitmapAsLockScreen(bitmap: Bitmap) {
        setAs(bitmap = bitmap, AndroidWallpaperManager.FLAG_LOCK)
        bitmap.recycle()
    }

    actual fun setBitMapAsBothScreens(bitmap: Bitmap) {
        setAs(bitmap = bitmap, AndroidWallpaperManager.FLAG_SYSTEM)
        setAs(bitmap = bitmap, AndroidWallpaperManager.FLAG_LOCK)
        bitmap.recycle()
    }

    private fun setAs(bitmap: Bitmap,flag: Int) {
        manager?.let { nonNullManager ->
            with(nonNullManager) {
                suggestDesiredDimensions(screenResolution.width, screenResolution.height)
                val width = getDesiredMinimumWidth()
                val height = getDesiredMinimumHeight()
                val wallpaper = bitmap.scale(width = width, height = height)
                try {
                    setBitmap(wallpaper,null,true,flag)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                wallpaper.recycle()
            }
        }
    }
}