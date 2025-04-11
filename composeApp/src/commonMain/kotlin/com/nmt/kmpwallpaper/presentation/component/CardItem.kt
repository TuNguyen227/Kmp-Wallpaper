package com.nmt.kmpwallpaper.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.SingletonImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import com.nmt.kmpwallpaper.model.Photo

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    imageModifier: Modifier =Modifier,
    photo: Photo,
    onItemClick: (Photo) -> Unit,
    borderStrokeEnabled: Boolean = false
) {
    var isItemClick by rememberSaveable(photo.id) {
        mutableStateOf(false)
    }
    OutlinedCard(
        modifier = modifier.then(imageModifier),
        onClick = {
            isItemClick = !isItemClick
            onItemClick(photo)
        },
        border = if (isItemClick && borderStrokeEnabled) BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer)
            else CardDefaults.outlinedCardBorder()
        ,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box{
            PhotoView(photo)
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
        }
    }
}

@Composable
fun PhotoView(
    photo: Photo
) {
    val context = LocalPlatformContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(photo.imageUrl)
        .build()
    val loader = SingletonImageLoader.get(context)
    AsyncImage(
        model = imageRequest,
        contentDescription = "Photo ${photo.id ?: photo.imageUrl}",
        imageLoader = loader,
        contentScale = ContentScale.FillBounds
    )
}
