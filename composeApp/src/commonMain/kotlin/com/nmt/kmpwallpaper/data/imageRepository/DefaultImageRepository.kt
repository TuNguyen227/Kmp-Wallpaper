package com.nmt.kmpwallpaper.data.imageRepository

import androidx.paging.PagingConfig
import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.network.AppSourceApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class DefaultImageRepository(
    private val appSourceApi: AppSourceApi,
) : ImageRepository,
    KoinComponent {
    private val query = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val pagingTrendingData: Flow<PagingData<Photo>> =
        query.flatMapLatest { input ->
            Pager(
                config =
                    PagingConfig(
                        pageSize = 16,
                        prefetchDistance = 4,
                    ),
                pagingSourceFactory = {
                    ImagePagingSource(
                        apiInvoke = { page ->
                            appSourceApi.search(
                                query = input ?: "trending",
                                page = page.toString(),
                            )
                        },
                    )
                },
            ).flow
        }
    override val pagingNewsData: Lazy<Flow<PagingData<Photo>>> =
        lazy {
            Pager(
                config =
                    PagingConfig(
                        pageSize = 16,
                        prefetchDistance = 4,
                    ),
                pagingSourceFactory = {
                    ImagePagingSource(
                        apiInvoke = { page ->
                            appSourceApi.getNews(page = page.toString())
                        },
                    )
                },
            ).flow
        }

    override fun query(query: String?) {
        this.query.update { query }
    }
}
