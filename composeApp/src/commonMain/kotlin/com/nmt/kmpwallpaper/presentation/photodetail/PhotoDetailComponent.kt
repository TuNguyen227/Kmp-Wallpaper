package com.nmt.kmpwallpaper.presentation.photodetail

import coil3.Bitmap
import com.arkivanov.decompose.ComponentContext
import com.nmt.kmpwallpaper.infrastructure.wallpaper.WallpaperManager
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.Settings
import io.github.tunguyen227.Platform

class PhotoDetailComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {
    private var bitmap: Bitmap? = null
    fun onHandlePhoto(settings: Settings) {
        bitmap?.let { nonNullBitmap ->
            when(settings) {
                Settings.ACTION_SET_HOME_SCREEN -> {
                    WallpaperManager.setBitmapAsHomeScreen(nonNullBitmap)
                }
                Settings.ACTION_SET_LOCK_SCREEN -> {
                    WallpaperManager.setBitmapAsLockScreen(nonNullBitmap)
                }

                Settings.ACTION_SET_HOME_N_LOCK -> {
                    WallpaperManager.setBitMapAsBothScreens(nonNullBitmap)
                }
                else -> {}
            }
        }
    }

    fun onPhotoLoaded(bitmap: Bitmap) {
        if (this.bitmap == null) {
            this.bitmap = bitmap
        }
    }
}