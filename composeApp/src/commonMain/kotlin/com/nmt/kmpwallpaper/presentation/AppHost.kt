package com.nmt.kmpwallpaper.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.allowConversionToBitmap
import coil3.request.crossfade
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpcore.presentation.theme.getLightColorScheme
import com.nmt.kmpcore.presentation.theme.getTypography
import com.nmt.kmpwallpaper.infrastructure.resolution.Size
import com.nmt.kmpwallpaper.presentation.flash.FlashScreenRoute
import com.nmt.kmpwallpaper.presentation.home.HomeScreen
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailRoute
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailScreen
import io.github.xxfast.decompose.router.LocalRouterContext
import io.github.xxfast.decompose.router.stack.RoutedContent
import io.github.xxfast.decompose.router.stack.rememberRouter
import kotlinx.serialization.Serializable

@Composable
fun AppHost(root: RootComponent, deviceSize: Size) {
    MaterialTheme(
        colorScheme = getLightColorScheme(),
        typography = getTypography()
    ) {
        setSingletonImageLoaderFactory {
            ImageLoader.Builder(it)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
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
                    },
                    deviceSize
                )
                is Child.FlashScreen -> FlashScreenRoute(
                    component = instance.component,
                    onNavigate = { config ->
                        root.navigateAndClearStack(
                            configuration = config
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