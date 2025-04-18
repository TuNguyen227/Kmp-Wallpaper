package com.nmt.kmpwallpaper.data

import app.cash.paging.PagingData
import com.nmt.kmpcore.network.model.ResultWrapper
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.network.model.response.SearchResponse
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    val pagingData : Flow<PagingData<Photo>>
}