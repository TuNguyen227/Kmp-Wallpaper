package com.nmt.kmpwallpaper

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpwallpaper.data.createDataStore
import com.nmt.kmpwallpaper.data.imageRepository.ImageRepository
import com.nmt.kmpwallpaper.di.KoinManager
import com.nmt.kmpwallpaper.presentation.AppHost
import com.nmt.kmpwallpaper.presentation.Child
import com.nmt.kmpwallpaper.presentation.ChildConfiguration
import com.nmt.kmpwallpaper.presentation.flash.FlashComponent
import com.nmt.kmpwallpaper.presentation.home.HomeComponent
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailComponent

fun MainViewController() = ComposeUIViewController {
    val koin = KoinManager.initKoin()
    val dataStore = createDataStore()
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
                                imageRepository = koin.inject<ImageRepository>().value,
                                changeLanguageUseCase = koin.get()
                            )
                        )
                    }
                    ChildConfiguration.Flash -> {
                        Child.FlashScreen(
                            FlashComponent(
                                componentContext = context,
                                dataStore = dataStore
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
                            componentContext = context,
                            dataStore = dataStore
                        )
                    )
                }
            }
        )
    }
    AppHost(root)
}