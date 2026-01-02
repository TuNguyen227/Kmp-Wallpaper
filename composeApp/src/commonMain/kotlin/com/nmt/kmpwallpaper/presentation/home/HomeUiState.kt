package com.nmt.kmpwallpaper.presentation.home

import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.model.AppSetting
import com.nmt.kmpwallpaper.presentation.home.model.SubCategory

data class HomeUiState(
    val categories : List<Photo> = listOf(),
    val ui : HomeUi = HomeUi()
)

data class HomeUi(
    val setting: String = "Setting",
    val category: String = "Category",
    val viewAll: String = "View all",
    val apply: String = "Apply",
    val youHaveNotView: String = "You have not viewed any images.",
    val subCategories: List<SubCategory> = listOf(),
    val appSettings: List<AppSetting> = listOf()
)