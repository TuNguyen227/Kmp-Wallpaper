package com.nmt.kmpwallpaper.data


import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.nmt.kmpcore.network.model.ResultWrapper
import com.nmt.kmpcore.network.safeApiCall
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.network.AppSourceApi
import com.nmt.kmpwallpaper.network.model.response.SearchResponse

class ImagePagingSource(
    private val appSourceApi: AppSourceApi
) : PagingSource<Int, Photo>() {
    private var query : String? = null
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val currentPage = params.key ?: 1
        val data = fetch(query = query ?: "trending", page = currentPage)
        println("Check state load $currentPage $data")
        return data?.let { nonNullData ->
            LoadResult.Page(
                data = nonNullData.photos.map { it.toPhoto() },
                prevKey = if (currentPage == 1) null else currentPage -1,
                nextKey = (currentPage + 1).takeIf { nonNullData.nextPage != null }
            )
        } ?: LoadResult.Error(Throwable("test"))
    }

    private suspend fun fetch(query: String, page: Int) : SearchResponse? {
        val response =  safeApiCall<SearchResponse> { appSourceApi.search(query = query, page = page.toString()) }
        return when(response) {
            is ResultWrapper.Success -> {
                response.data
            }
            is ResultWrapper.Error -> {
                null
            }
        }
    }

    fun setQuery(query: String) {
        this.query = query
    }
}