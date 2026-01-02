package com.nmt.kmpwallpaper.infrastructure.wallpaper

import coil3.Bitmap
import io.github.tunguyen227.PlatformContext

expect object WallpaperManager {
    fun initialize(platformContext: PlatformContext)

    fun setBitmapAsHomeScreen(bitmap: Bitmap)

    fun setBitmapAsLockScreen(bitmap: Bitmap)

    fun setBitMapAsBothScreens(bitmap: Bitmap)
}