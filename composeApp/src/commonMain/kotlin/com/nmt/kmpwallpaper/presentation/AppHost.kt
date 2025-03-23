package com.nmt.kmpwallpaper.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.getValue
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpcore.presentation.theme.getLightColorScheme
import com.nmt.kmpcore.presentation.theme.getTypography
import com.nmt.kmpwallpaper.presentation.flash.FlashScreenRoute
import com.nmt.kmpwallpaper.presentation.home.HomeScreen

@Composable
fun AppHost(root: RootComponent) {
    MaterialTheme(
        colorScheme = getLightColorScheme(),
        typography = getTypography()
    ) {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when(val instance = child.instance) {
                is Child.Home -> HomeScreen(
                    instance.component
                )
                is Child.FlashScreen -> FlashScreenRoute(
                    component = instance.component,
                    onNavigate = { config ->
                        root.navigate(
                            configuration = config,
                            onComplete = {
                                if (it) {
                                    instance.component.resetState()
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}