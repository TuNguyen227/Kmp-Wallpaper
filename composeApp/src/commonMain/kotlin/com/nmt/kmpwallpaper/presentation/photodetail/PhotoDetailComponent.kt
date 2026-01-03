package com.nmt.kmpwallpaper.presentation.photodetail

import coil3.Bitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.nmt.kmpwallpaper.infrastructure.wallpaper.WallpaperManager
import com.nmt.kmpwallpaper.model.AppSetting
import com.nmt.kmpwallpaper.util.StringProvider

class PhotoDetailComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {
    private val _uiState = MutableValue(PhotoUiState())
    val uiState: Value<PhotoUiState> = _uiState
    private var bitmap: Bitmap? = null

    private val _isSetSuccessful = MutableValue(false)
    val isSetSuccessful: Value<Boolean> = _isSetSuccessful

    init {
        updateUiState()
    }

    fun onHandlePhoto(action: AppSetting) {
        bitmap?.let { nonNullBitmap ->
            when (action) {
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
            _isSetSuccessful.update { true }
        }
    }

    fun onPhotoLoaded(bitmap: Bitmap) {
        if (this.bitmap == null) {
            this.bitmap = bitmap
        }
    }

    private fun updateUiState() {
        _uiState.update {
            it.copy(
                actionSettings =
                    listOf(
                        AppSetting.ActionSetAsHome(nameValue = StringProvider.actionSetHome),
                        AppSetting.ActionSetAsLock(nameValue = StringProvider.actionSetLock),
                        AppSetting.ActionSetBoth(nameValue = StringProvider.actionSetBoth),
                    ),
            )
        }
    }

    fun onClear() {
        println("Check on clear")
        WallpaperManager.clear(bitmap)
    }
}
