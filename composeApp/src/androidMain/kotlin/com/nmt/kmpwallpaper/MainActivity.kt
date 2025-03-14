package com.nmt.kmpwallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.retainedComponent
import com.nmt.kmpcore.presentation.navigation.Configuration
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpwallpaper.presentation.AppHost
import com.nmt.kmpwallpaper.presentation.Child
import com.nmt.kmpwallpaper.presentation.home.HomeComponent
import com.nmt.kmpwallpaper.presentation.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent {
            RootComponent(
                componentContext = it,
                screenFactory = { config, context ->
                    when(config) {
                        Configuration.HomeScreen -> {
                            Child.Home(
                                HomeComponent(
                                    context
                                )
                            )
                        }
                    }
                }
            )
        }
        setContent {
            AppHost(root)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
}