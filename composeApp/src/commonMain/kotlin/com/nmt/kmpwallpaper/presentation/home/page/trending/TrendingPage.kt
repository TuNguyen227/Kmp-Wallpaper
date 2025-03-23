package com.nmt.kmpwallpaper.presentation.home.page.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.component.CardItem

@Composable
fun TrendingPage(
    state: LazyGridState,
    photos: List<Photo>,
    onLikeClick: (Photo) -> Unit,
    onItemClick: (Photo) -> Unit
) {
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(2),
        content = {
            items(
                items = photos
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