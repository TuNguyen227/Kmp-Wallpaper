package com.nmt.kmpwallpaper.presentation.home.page.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.component.CardItem

@Composable
fun ImagePage(
    modifier: Modifier,
    state: LazyGridState,
    photos: List<Photo>,
    onItemClick: (Photo) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        columns = GridCells.Fixed(2),
        content = {
            items(photos) { photo ->
                CardItem(
                    modifier = Modifier.height(300.dp),
                    photo = photo,
                    onItemClick = {
                        onItemClick(photo)
                    }
                )
            }
        },
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    )
}