package com.nmt.kmpwallpaper.data.imageRepository

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.nmt.kmpcore.network.model.ResultWrapper
import com.nmt.kmpcore.network.safeApiCall
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.network.model.response.SearchResponse
import io.ktor.client.statement.HttpResponse

class ImagePagingSource(
    private val apiInvoke: suspend (Int) -> HttpResponse,
) : PagingSource<Int, Photo>() {
    private var query: String? = null

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val currentPage = params.key ?: 1
        val data =
            fetch<SearchResponse>(
                httpResponse = apiInvoke.invoke(currentPage),
            )
        return data?.let { nonNullData ->
            LoadResult.Page(
                data = nonNullData.photos.map { it.toPhoto() },
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = (currentPage + 1).takeIf { nonNullData.nextPage != null },
            )
        } ?: LoadResult.Error(Throwable("Unknown Error"))
    }

    private suspend inline fun <reified T> fetch(httpResponse: HttpResponse): T? {
        val response = safeApiCall<T> { httpResponse }
        return when (response) {
            is ResultWrapper.Success -> {
                response.data
            }
            is ResultWrapper.Error -> {
                null
            }
        }
    }

    fun setQuery(query: String?) {
        this.query = query
    }
}
