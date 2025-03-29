package com.nmt.kmpwallpaper.presentation.photodetail

import coil3.Bitmap
import com.arkivanov.decompose.ComponentContext
import com.nmt.kmpwallpaper.infrastructure.wallpaper.WallpaperManager
import io.github.tunguyen227.Platform

class PhotoDetailComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {
    private var bitmap: Bitmap? = null
    fun onHandlePhoto() {
        println(
            "onHandlePhoto"
        )
        bitmap?.let {
            WallpaperManager.setBitmapAsHomeScreen(it)
        }
    }

    fun onPhotoLoaded(bitmap: Bitmap) {
        println(
            "onPhotoLoaded ${Platform.name}"
        )
        this.bitmap = bitmap
    }
}