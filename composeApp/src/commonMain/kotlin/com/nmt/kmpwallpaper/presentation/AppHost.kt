package com.nmt.kmpwallpaper.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import app.lexilabs.basic.ads.BasicAds
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.CachePolicy
import coil3.request.allowConversionToBitmap
import coil3.request.crossfade
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.nmt.kmpcore.presentation.navigation.RootComponent
import com.nmt.kmpcore.presentation.theme.getLightColorScheme
import com.nmt.kmpcore.presentation.theme.getTypography
import com.nmt.kmpwallpaper.infrastructure.resolution.Size
import com.nmt.kmpwallpaper.model.IntentEvent
import com.nmt.kmpwallpaper.presentation.flash.FlashScreenRoute
import com.nmt.kmpwallpaper.presentation.home.HomeScreen
import com.nmt.kmpwallpaper.presentation.photodetail.PhotoDetailRoute
import com.stevdza_san.demo.presentation.component.AppRatingDialog

@OptIn(DependsOnGoogleMobileAds::class)
@Composable
fun AppHost(
    root: RootComponent,
    deviceSize: Size,
    intentEvent: (IntentEvent) -> Unit = {},
) {
    BasicAds.Initialize()
    MaterialTheme(
        colorScheme = getLightColorScheme(),
        typography = getTypography(),
    ) {
        setSingletonImageLoaderFactory {
            ImageLoader
                .Builder(it)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .allowConversionToBitmap(true)
                .build()
        }
        val childStack by root.childStack.subscribeAsState()
        var showRatingDialog by remember { mutableStateOf(false) }
        if (showRatingDialog) {
            AppRatingDialog(
                playStoreLink = IntentEvent.RATING.link,
                appStoreLink = IntentEvent.RATING.link,
                initialDelayInDays = 5,
                title = {
                    Text(
                        text = "Enjoying our App?",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier =
                            Modifier.semantics {
                                this.contentDescription = "Rating header"
                            },
                    )
                },
                content = {
                    Text(
                        text = "If you're happy with the app, please leave us a review!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier =
                            Modifier.semantics {
                                this.contentDescription = "Rating content"
                            },
                    )
                },
                dismissText = "NO",
                confirmText = "OK",
                onDismiss = {
                    showRatingDialog = false
                },
            )
        }
        Children(
            stack = childStack,
            animation = stackAnimation(slide()),
        ) { child ->
            when (val instance = child.instance) {
                is Child.Home ->
                    HomeScreen(
                        instance.component,
                        onPhotoClick = { data ->
                            root.navigate(
                                configuration =
                                    ChildConfiguration.PhotoDetail(
                                        data,
                                    ),
                            )
                        },
                        deviceSize,
                        { intent ->
                            when (intent) {
                                IntentEvent.RATING -> {
                                    showRatingDialog = true
                                }
                                else -> {}
                            }
                            intentEvent(intent)
                        },
                    )
                is Child.FlashScreen ->
                    FlashScreenRoute(
                        component = instance.component,
                        onNavigate = { config ->
                            root.navigateAndClearStack(
                                configuration = config,
                            )
                        },
                    )
                is Child.PhotoDetail -> {
                    PhotoDetailRoute(
                        component = instance.component,
                        photo = instance.photo,
                        onNavigateBack = {
                            root.popBackStack()
                        },
                    )
                }
            }
        }
    }
}
