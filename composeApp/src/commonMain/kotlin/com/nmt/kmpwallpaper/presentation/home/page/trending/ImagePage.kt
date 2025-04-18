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
import app.cash.paging.LoadStateError
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemContentType
import app.cash.paging.compose.itemKey
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.component.CardItem
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ImagePage(
    modifier: Modifier,
    state: LazyGridState,
    lazyPhotos: LazyPagingItems<Photo>? = null,
    photos: List<Photo>? = null,
    onItemClick: (Photo) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        columns = GridCells.Fixed(2),
        content = {
            lazyPhotos?.let {
                items(lazyPhotos.itemCount,
                    contentType = lazyPhotos.itemContentType()
                ) { index ->
                    val photo = lazyPhotos[index]
                    photo?.let { nonNullItem ->
                        CardItem(
                            modifier = Modifier.height(300.dp),
                            photo = nonNullItem,
                            onItemClick = {
                                onItemClick(nonNullItem)
                            }
                        )
                    }
                    if (lazyPhotos.loadState.append is LoadStateError) {
                        println("Check state ${(lazyPhotos.loadState.append as LoadStateError).error}")
                    }
                }
            } ?: run {
                photos?.let {
                    items(photos) {
                        CardItem(
                            modifier = Modifier.height(300.dp),
                            photo = it,
                            onItemClick = {
                                onItemClick(it)
                            }
                        )
                    }
                }
            }
        },
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    )
}