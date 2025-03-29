package com.nmt.kmpwallpaper

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.PlatformContext
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpwallpaper.data.DefaultImageRepository
import com.nmt.kmpwallpaper.data.ImageRepository
import com.nmt.kmpwallpaper.di.koin
import com.nmt.kmpwallpaper.network.AppDataSource
import com.nmt.kmpwallpaper.presentation.AppHost
import com.nmt.kmpwallpaper.presentation.Child
import com.nmt.kmpwallpaper.presentation.ChildConfiguration
import com.nmt.kmpwallpaper.presentation.flash.FlashComponent
import com.nmt.kmpwallpaper.presentation.home.HomeComponent
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailComponent

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
                    is ChildConfiguration.PhotoDetail -> {
                        Child.PhotoDetail(
                            PhotoDetailComponent(
                                context
                            ),
                            photo = config.data
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