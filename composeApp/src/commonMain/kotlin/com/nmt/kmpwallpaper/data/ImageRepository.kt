package com.nmt.kmpwallpaper.data

import com.nmt.kmpcore.network.model.ResultWrapper
import com.nmt.kmpwallpaper.network.model.response.SearchResponse

interface ImageRepository {
    suspend fun search(
        query: String,
        page: String
    ) : SearchResponse?
}