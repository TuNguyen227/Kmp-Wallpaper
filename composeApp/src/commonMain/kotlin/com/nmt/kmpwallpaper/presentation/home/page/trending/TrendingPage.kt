package com.nmt.kmpwallpaper.presentation.home.page.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.component.CardItem

@Composable
fun TrendingPage(
    state: LazyListState,
    photos: List<Photo>,
    onLikeClick: (Photo) -> Unit,
    onItemClick: (Photo) -> Unit,
    onScrollEnd: (Int, Int) -> Unit
) {
    val photoScrollState = rememberLazyGridState(
    )
    LaunchedEffect(photoScrollState.isScrollInProgress) {
        if (!photoScrollState.isScrollInProgress) {
            onScrollEnd(photoScrollState.firstVisibleItemIndex, photoScrollState.firstVisibleItemScrollOffset)
        }
    }
    println(
        "photoScrollState $photoScrollState"
    )
    LazyVerticalGrid(
        state = photoScrollState,
        columns = GridCells.Fixed(1),
        content = {
            items(
                items = photos,
                key = { it.imageUrl }
            ) { photo ->
                CardItem(
                    photo = photo,
                    onItemClick = {
                        onItemClick(photo)
                    },
                    isLikable = true,
                    onLikeLick = {
                        onLikeClick(photo)
                    }
                )
            }
        },
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    )
}