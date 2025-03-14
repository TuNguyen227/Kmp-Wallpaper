package com.nmt.kmpwallpaper.presentation

import com.nmt.kmpcore.presentation.navigation.AppChild
import com.nmt.kmpwallpaper.presentation.home.HomeComponent

sealed interface Child : AppChild {
    data class Home(val component: HomeComponent) : AppChild
}