package com.nmt.kmpwallpaper.data

import com.nmt.kmpcore.network.model.ResultWrapper
import com.nmt.kmpcore.network.safeApiCall
import com.nmt.kmpwallpaper.network.AppSourceApi
import com.nmt.kmpwallpaper.network.model.response.SearchResponse
import org.koin.core.component.KoinComponent

class DefaultImageRepository(
    private val appDataSource: AppSourceApi
) : ImageRepository,KoinComponent {
    override suspend fun search(query: String, page: String): SearchResponse? {
        val result = safeApiCall<SearchResponse> {
            appDataSource.search(
                query = query,
                page = page
            )
        }
        return when(result) {
            is ResultWrapper.Success -> {
                result.data
            }
            is ResultWrapper.Error -> {
                null
            }
        }
    }
}