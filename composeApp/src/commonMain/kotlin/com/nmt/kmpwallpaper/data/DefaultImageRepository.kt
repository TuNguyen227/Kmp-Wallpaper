package com.nmt.kmpwallpaper.data

import androidx.paging.PagingConfig
import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.network.AppSourceApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class DefaultImageRepository(
    private val appSourceApi: AppSourceApi
) : ImageRepository,KoinComponent {
    private val query = MutableStateFlow<String?>(null)
    private val pager : MutableStateFlow<Pager<Int,Photo>> = MutableStateFlow(Pager(
        config = PagingConfig(
            pageSize = 16,
            prefetchDistance = 4
        ),
        pagingSourceFactory = {
            ImagePagingSource(appSourceApi)
        },
    )
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    override val pagingData: Flow<PagingData<Photo>> = query.flatMapLatest { input ->
        Pager(
            config = PagingConfig(
                pageSize = 16,
                prefetchDistance = 4
            ),
            pagingSourceFactory = {
                ImagePagingSource(appSourceApi).apply {
                    setQuery(input)
                }
            },
        ).flow
    }

    override fun query(query: String?) {
        this.query.update { query }
    }
}