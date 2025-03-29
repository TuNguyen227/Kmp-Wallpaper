package com.nmt.kmpwallpaper.infrastructure.wallpaper

import coil3.Bitmap
import io.github.tunguyen227.PlatformContext

private typealias AndroidWallpaperManager = android.app.WallpaperManager

actual object WallpaperManager {
    private var manager : AndroidWallpaperManager? = null
    actual fun initialize(platformContext: PlatformContext) {
        manager = AndroidWallpaperManager.getInstance(platformContext.applicationContext)
    }
    actual fun setBitmapAsHomeScreen(bitmap: Bitmap) {
        println(
            "setBitmapAsHomeScreen $bitmap $manager"
        )
        manager?.setBitmap(bitmap,null,true,AndroidWallpaperManager.FLAG_SYSTEM)
    }

    actual fun setBitmapAsLockScreen(bitmap: Bitmap) {
        manager?.setBitmap(bitmap,null,true,AndroidWallpaperManager.FLAG_LOCK)
    }
}