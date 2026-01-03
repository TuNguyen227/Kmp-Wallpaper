package com.nmt.kmpwallpaper.infrastructure.wallpaper

import coil3.Bitmap
import io.github.tunguyen227.PlatformContext


actual object WallpaperManager{
    actual fun initialize(platformContext: PlatformContext) {}
    actual fun setBitmapAsHomeScreen(bitmap: Bitmap) {}
    actual fun setBitmapAsLockScreen(bitmap: Bitmap) {}
    actual fun setBitMapAsBothScreens(bitmap: Bitmap) {}
}