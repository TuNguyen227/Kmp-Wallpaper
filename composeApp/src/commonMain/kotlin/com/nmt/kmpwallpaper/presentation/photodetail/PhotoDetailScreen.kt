package com.nmt.kmpwallpaper.presentation.photodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.ImageResult
import coil3.request.SuccessResult
import coil3.request.allowConversionToBitmap
import coil3.toBitmap
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_back
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_painter
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.DrawerBody
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.Settings
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource

@Composable
fun PhotoDetailRoute(
    component: PhotoDetailComponent,
    photo: Photo,
    onNavigateBack: () -> Unit
) {
    PhotoDetailScreen(
        component = component,
        photo = photo,
        onNavigateBack = onNavigateBack,
        onHandlePhoto = {
            component.onHandlePhoto()
        },
        onPhotoLoaded = component::onPhotoLoaded
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    component: PhotoDetailComponent,
    photo: Photo,
    onNavigateBack: () -> Unit,
    onHandlePhoto: () -> Unit,
    onPhotoLoaded: (Bitmap) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val localDensity = LocalDensity.current
        var centerBottomPosition by remember {
            mutableStateOf(Offset.Zero)
        }
        var isHandlePhoto by remember {
            mutableStateOf(false)
        }
        Icon(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                .size(32.dp).align(Alignment.TopStart)
                .clickable {
                    println(
                        "onNavigateBack"
                    )
                    onNavigateBack()
                }
            ,
            imageVector = vectorResource(Res.drawable.ic_back),
            contentDescription = "Icon back"
        )
        PhotoPreview(
            modifier = Modifier.align(Alignment.Center)
            ,
            photo = photo,
            onPosition = {
                centerBottomPosition = it
            },
            onPhotoLoaded = onPhotoLoaded
        )
        Icon(
            modifier = Modifier
                .offset(
                    x = with(localDensity) { centerBottomPosition.x.toDp() - 36.5.dp } ,
                    y = with(localDensity) { centerBottomPosition.y.toDp() - 36.5.dp }
                )
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable {
                    //onHandlePhoto()
                    isHandlePhoto = true
                }
            ,
            imageVector = vectorResource(Res.drawable.ic_painter),
            contentDescription = "Icon back",
            tint = Color.Unspecified
        )

        if (isHandlePhoto) {
            ModalBottomSheet(
                onDismissRequest = {},
                contentColor = MaterialTheme.colorScheme.background,
                dragHandle = null
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
//                    val options = listOf(
//                        Settings("Set on home screen"),
//                        "Set on lock screen",
//                        "Set on both screen"
//                    )
//
//                    DrawerBody(
//                        items = options,
//
//                    )
                }
            }
        }
    }
}

@Composable
private fun PhotoPreview(
    modifier: Modifier,
    photo: Photo,
    onPosition: (Offset) -> Unit,
    onPhotoLoaded: (Bitmap) -> Unit
) {
    val scope = rememberCoroutineScope()
    var centerBottomPosition by remember {
        mutableStateOf(Offset.Zero)
    }
    LaunchedEffect(centerBottomPosition) {
        if (centerBottomPosition != Offset.Zero) {
            onPosition(centerBottomPosition)
        }
    }
    Surface(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .onGloballyPositioned { layoutCoordinates ->
                // Get the size and position of the layout
                //if (centerBottomPosition == Offset.Zero) {
                val size = layoutCoordinates.size
                val position = layoutCoordinates.positionInWindow()

                // Calculate center bottom position
                val centerX = position.x + size.width / 2
                val bottomY = position.y + size.height

                // Update the state with the center bottom position
                centerBottomPosition = Offset(centerX, bottomY)
                //}
            }
        ,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 10.dp
    ) {
        val imageRequest = ImageRequest.Builder(LocalPlatformContext.current)
            .data(photo.imageUrl)
            .allowConversionToBitmap(true)
            .build()
        val loader = ImageLoader.Builder(LocalPlatformContext.current)
            .build()
        scope.launch {
            val result = loader.execute(imageRequest)
            when(result) {
                is SuccessResult -> {
                    println(
                        "Success"
                    )
                    onPhotoLoaded(result.image.toBitmap())
                }

                else -> {}
            }
        }
        AsyncImage(
            model = imageRequest.data,
            contentDescription = "Detail Item"
        )
    }
}