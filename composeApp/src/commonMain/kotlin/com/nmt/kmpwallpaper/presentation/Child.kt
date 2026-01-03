package com.nmt.kmpwallpaper.presentation

import com.nmt.kmpcore.presentation.navigation.AppChild
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.flash.FlashComponent
import com.nmt.kmpwallpaper.presentation.home.HomeComponent
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailComponent

sealed interface Child : AppChild {
    data class FlashScreen(val component: FlashComponent) : AppChild
    data class Home(val component: HomeComponent) : AppChild
    data class PhotoDetail(val component: PhotoDetailComponent, val photo: Photo) : AppChild
}