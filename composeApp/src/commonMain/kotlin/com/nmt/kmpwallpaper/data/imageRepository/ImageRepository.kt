package com.nmt.kmpwallpaper.data.imageRepository

import app.cash.paging.PagingData
import com.nmt.kmpwallpaper.model.Photo
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    val pagingTrendingData: Flow<PagingData<Photo>>
    val pagingNewsData: Lazy<Flow<PagingData<Photo>>>

    fun query(query: String?)
}
