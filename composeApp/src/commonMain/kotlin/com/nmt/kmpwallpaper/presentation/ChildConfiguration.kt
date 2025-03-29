package com.nmt.kmpwallpaper.presentation

import com.nmt.kmpcore.presentation.navigation.Configuration
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.flash.FlashComponent
import com.nmt.kmpwallpaper.presentation.home.HomeComponent
import kotlinx.serialization.Serializable

@Serializable
sealed class ChildConfiguration : Configuration() {
    @Serializable
    data object Home: Configuration()

    @Serializable
    data object Flash : Configuration()

    @Serializable
    data class PhotoDetail(val data: Photo) : Configuration()
}