package com.nmt.kmpwallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.retainedComponent
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.nmt.kmpcore.presentation.navigation.Configuration
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpwallpaper.data.DefaultImageRepository
import com.nmt.kmpwallpaper.data.ImageRepository
import com.nmt.kmpwallpaper.di.koin
import com.nmt.kmpwallpaper.presentation.AppHost
import com.nmt.kmpwallpaper.presentation.Child
import com.nmt.kmpwallpaper.presentation.ChildConfiguration
import com.nmt.kmpwallpaper.presentation.flash.FlashComponent
import com.nmt.kmpwallpaper.presentation.flash.FlashScreenRoute
import com.nmt.kmpwallpaper.presentation.home.HomeComponent
import com.nmt.kmpwallpaper.presentation.home.HomeScreen
import org.koin.core.component.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
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