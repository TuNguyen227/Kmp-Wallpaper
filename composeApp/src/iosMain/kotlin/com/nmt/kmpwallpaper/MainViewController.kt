package com.nmt.kmpwallpaper

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpwallpaper.data.ImageRepository
import com.nmt.kmpwallpaper.di.koin
import com.nmt.kmpwallpaper.presentation.AppHost
import com.nmt.kmpwallpaper.presentation.Child
import com.nmt.kmpwallpaper.presentation.ChildConfiguration
import com.nmt.kmpwallpaper.presentation.flash.FlashComponent
import com.nmt.kmpwallpaper.presentation.home.HomeComponent

fun MainViewController() = ComposeUIViewController {
    val root = remember {
        RootComponent(
            componentContext = DefaultComponentContext(LifecycleRegistry()),
            initialConfiguration = ChildConfiguration.Flash,
            screenFactory = { config, context ->
                when(config) {
                    ChildConfiguration.Home -> {
                        Child.Home(
                            HomeComponent(
                                componentContext = context,
                                imageRepository = koin.inject<ImageRepository>().value
                            )
                        )
                    }
                    ChildConfiguration.Flash -> {
                        Child.FlashScreen(
                            FlashComponent(
                                context
                            )
                        )
                    }
                    else -> Child.FlashScreen(
                        FlashComponent(
                            context
                        )
                    )
                }
            }
        )
    }
    AppHost(root)
}