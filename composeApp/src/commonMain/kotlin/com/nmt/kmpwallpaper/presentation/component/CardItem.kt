package com.nmt.kmpwallpaper.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.category_place_holder
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_heart_selected
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_heart_unselected
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_wallpaper
import com.nmt.kmpwallpaper.composeApp.commonMain.image_place_holder
import com.nmt.kmpwallpaper.model.Photo
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.coil3.CoilImageState
import com.skydoves.landscapist.coil3.LocalCoilImageLoader
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    imageModifier: Modifier =Modifier,
    photo: Photo,
    onItemClick: (String) -> Unit,
    onLikeLick: (Boolean) -> Unit = {},
    isLikable : Boolean = false
) {
    val isLikeSelected = rememberSaveable(photo.id) {
        mutableStateOf(false)
    }
    val imageState = rememberSaveable(photo.id) {
        mutableStateOf(ImageState.INIT)
    }
    var isItemClick by rememberSaveable(photo.id) {
        mutableStateOf(false)
    }
    OutlinedCard(
        modifier = modifier,
        onClick = {
            println("onItemClick ${imageState.value} ${photo} ")
            isItemClick = !isItemClick
            photo.name?.let(onItemClick)
        },
        border = if (isItemClick && !isLikable) BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer)
            else CardDefaults.outlinedCardBorder()
        ,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box{
            CoilImage(
                imageModel = {
                    photo.imageUrl
                }
                ,
                modifier = imageModifier
                    .semantics {
                        this.contentDescription = "Card Item"
                    },
                loading = {
                    Icon(
                        modifier = imageModifier,
                        imageVector = if (isLikable) vectorResource(Res.drawable.image_place_holder)
                        else vectorResource(Res.drawable.category_place_holder),
                        contentDescription = "image place holder",
                        tint = Color.Unspecified
                    )
                },
                onImageStateChanged = {
                    when(it) {
                        is CoilImageState.Loading -> {
                            imageState.value = ImageState.LOADING
                        }
                        is CoilImageState.Success -> {
                            imageState.value = ImageState.SUCCESS
                        }
                        else -> {}
                    }
                }
            )
            photo.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                        .semantics {
                            this.contentDescription = it
                        }
                    ,
                    color = MaterialTheme.colorScheme.background
                )
            }
            if (isLikable && imageState.value == ImageState.SUCCESS) {
                IconButton(
                    onClick = {
                        isLikeSelected.value = !isLikeSelected.value
                        onLikeLick(isLikeSelected.value)
                    },
                    modifier = Modifier
                        .padding(start = 5.dp, bottom = 5.dp)
                        .align(Alignment.BottomStart)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.35f), CircleShape)
                    ,
                ) {
                    Icon(
                        imageVector = if (isLikeSelected.value) vectorResource(Res.drawable.ic_heart_selected)
                        else vectorResource(Res.drawable.ic_heart_unselected),
                        contentDescription = "Photo like icon",
                        modifier = Modifier
                            .size(24.dp)
                        ,
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

private enum class ImageState {
    INIT,
    LOADING,
    SUCCESS
}
