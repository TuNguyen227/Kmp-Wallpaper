package com.nmt.kmpwallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.retainedComponent
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpwallpaper.data.ImageRepository
import com.nmt.kmpwallpaper.di.koin
import com.nmt.kmpwallpaper.infrastructure.wallpaper.WallpaperManager
import com.nmt.kmpwallpaper.presentation.AppHost
import com.nmt.kmpwallpaper.presentation.Child
import com.nmt.kmpwallpaper.presentation.ChildConfiguration
import com.nmt.kmpwallpaper.presentation.flash.FlashComponent
import com.nmt.kmpwallpaper.presentation.home.HomeComponent
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        WallpaperManager.initialize(this)
        val root = retainedComponent {
            RootComponent(
                componentContext = it,
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
                                    componentContext = context,
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
        setContent {
            AppHost(root)
        }
    }
}

class Test()