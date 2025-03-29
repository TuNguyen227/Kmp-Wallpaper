package com.nmt.kmpwallpaper.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.allowConversionToBitmap
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpcore.presentation.theme.getLightColorScheme
import com.nmt.kmpcore.presentation.theme.getTypography
import com.nmt.kmpwallpaper.presentation.flash.FlashScreenRoute
import com.nmt.kmpwallpaper.presentation.home.HomeScreen
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailRoute
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailScreen

@Composable
fun AppHost(root: RootComponent) {
    MaterialTheme(
        colorScheme = getLightColorScheme(),
        typography = getTypography()
    ) {
        setSingletonImageLoaderFactory {
            ImageLoader.Builder(it)
                .allowConversionToBitmap(true)
                .build()
        }
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when(val instance = child.instance) {
                is Child.Home -> HomeScreen(
                    instance.component,
                    onPhotoClick = { data ->
                        root.navigate(
                            configuration = ChildConfiguration.PhotoDetail(
                                data
                            )
                        )
                    }
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
                is Child.PhotoDetail -> {
                    PhotoDetailRoute(
                        component = instance.component,
                        photo = instance.photo,
                        onNavigateBack = {
                            root.popBackStack()
                        }
                    )
                }
            }
        }
    }
}