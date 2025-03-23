package com.nmt.kmpwallpaper.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.nmt.kmpwallpaper.data.ImageRepository
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.network.model.response.CategoryModel
import com.nmt.kmpwallpaper.presentation.ScreenComponent
import com.nmt.kmpwallpaper.presentation.home.page.new.NewComponent
import com.nmt.kmpwallpaper.presentation.home.page.Page
import com.nmt.kmpwallpaper.presentation.home.page.PageConfiguration
import com.nmt.kmpwallpaper.presentation.home.page.recent.RecentComponent
import com.nmt.kmpwallpaper.presentation.home.page.trending.TrendingComponent
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeComponent(
    componentContext: ComponentContext,
    private val imageRepository: ImageRepository
) : ScreenComponent,IHomeComponent, KoinComponent, ComponentContext by componentContext  {
    private val _uiState = MutableValue(HomeUiState())
    val uiState : Value<HomeUiState> = _uiState
    val trendingComponent = TrendingComponent(componentContext)
    private val firebaseDatabase by inject<FirebaseDatabase>()
    private var query: String = ""
    private val scope = coroutineScope()
    private val imageJob = coroutineScope()

    private val pageNavigation = PagesNavigation<PageConfiguration>()

    val page = childPages(
        source = pageNavigation,
        serializer = PageConfiguration.serializer(),
        initialPages = {
            Pages(
                items = listOf(
                    PageConfiguration.Trending,
                    PageConfiguration.Recent,
                    PageConfiguration.New
                ),
                selectedIndex = 0
            )
        },
        handleBackButton = true
    ) { configuration, componentContext ->  
        when(configuration) {
            PageConfiguration.Trending -> {
                Page.Trending(
                    TrendingComponent(
                    componentContext
                )
                )
            }
            PageConfiguration.Recent -> {
                Page.Recent(
                    RecentComponent(
                        componentContext
                    )
                )
            }
            PageConfiguration.New -> {
                Page.New(
                    NewComponent(
                        componentContext
                    )
                )
            }
        }
    }

    fun selectPage(index: Int, onComplete: () -> Unit = {} ) {
        pageNavigation.select(index = index, onComplete = { _,_ ->
            onComplete()
        })
    }

    init {
        scope.launch {
            getCategories()
        }
        imageJob.launch {
            getPhotos()
        }
    }

    override fun resetState() {

    }

    fun onCategoryClick(photo: Photo) {
        imageJob.launch {
            photo.name?.let {
                if (query.contains(it)) {
                    query = query.replace("$it ","")
                } else {
                    query += "$it "
                }
                getPhotos()
            }
        }
    }

    private suspend fun getCategories() {
        firebaseDatabase.reference("Category").valueEvents.collect {
            val snapshot = it.value<List<CategoryModel>>()
            _uiState.update { state ->
                state.copy(
                    categories = snapshot.map { model -> model.toPhoto() }
                )
            }
        }
    }

    private suspend fun getPhotos() {
        val request = query.ifEmpty {
            "trending"
        }.trim()
        imageRepository.search(request,"1")?.photos?.map {
            it.toPhoto()
        }?.let { photos ->
            _uiState.update {
                it.copy(images = photos)
            }
        }
    }

    class Factory(
        private val imageRepository: ImageRepository
    ) : IHomeComponent.Factory {
        override fun invoke(componentContext: ComponentContext): HomeComponent {
            return HomeComponent(
                componentContext = componentContext,
                imageRepository = imageRepository
            )
        }
    }

}

interface IHomeComponent {
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
        ) : HomeComponent
    }
}