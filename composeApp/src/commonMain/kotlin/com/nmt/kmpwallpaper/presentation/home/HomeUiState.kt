package com.nmt.kmpwallpaper.presentation.home

import com.nmt.kmpwallpaper.model.Photo

data class HomeUiState(
    val images: List<Photo> = listOf(),
    val categories : List<Photo> = listOf()
)