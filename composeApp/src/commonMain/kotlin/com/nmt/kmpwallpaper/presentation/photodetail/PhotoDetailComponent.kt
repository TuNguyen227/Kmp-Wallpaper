package com.nmt.kmpwallpaper.presentation.photodetail

import coil3.Bitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.nmt.kmpwallpaper.infrastructure.wallpaper.WallpaperManager
import com.nmt.kmpwallpaper.model.AppSetting

class PhotoDetailComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {
    private val _uiState = MutableValue(PhotoUiState())
    val uiState : Value<PhotoUiState> = _uiState
    private var bitmap: Bitmap? = null
    fun onHandlePhoto(action: AppSetting) {
        bitmap?.let { nonNullBitmap ->
            when(action) {
                is AppSetting.ActionSetAsHome -> {
                    WallpaperManager.setBitmapAsHomeScreen(nonNullBitmap)
                }
                is AppSetting.ActionSetAsLock -> {
                    WallpaperManager.setBitmapAsLockScreen(nonNullBitmap)
                }

                is AppSetting.ActionSetBoth -> {
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