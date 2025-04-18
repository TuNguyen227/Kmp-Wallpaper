package com.nmt.kmpwallpaper.data

import androidx.paging.PagingConfig
import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.nmt.kmpwallpaper.model.Photo
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class DefaultImageRepository(
    private val imagePagingSource: ImagePagingSource
) : ImageRepository,KoinComponent {
    private val pager : Pager<Int,Photo> = Pager(
        config = PagingConfig(
            pageSize = 16,
            prefetchDistance = 4
        ),
        pagingSourceFactory = {
            imagePagingSource
        }
    )
    override val pagingData: Flow<PagingData<Photo>> = pager.flow
}