package com.nmt.kmpwallpaper.presentation.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.paging.cachedIn
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.nmt.kmpcore.domain.ChangeLanguageUseCase
import com.nmt.kmpcore.infrastructure.provider.Language
import com.nmt.kmpcore.infrastructure.provider.LanguageProvider
import com.nmt.kmpwallpaper.data.imageRepository.ImageRepository
import com.nmt.kmpwallpaper.model.AppSetting
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.network.model.response.CategoryModel
import com.nmt.kmpwallpaper.presentation.ScreenComponent
import com.nmt.kmpwallpaper.presentation.home.model.SubCategory
import com.nmt.kmpwallpaper.presentation.home.page.Page
import com.nmt.kmpwallpaper.presentation.home.page.PageConfiguration
import com.nmt.kmpwallpaper.presentation.home.page.new.NewComponent
import com.nmt.kmpwallpaper.presentation.home.page.recent.RecentComponent
import com.nmt.kmpwallpaper.presentation.home.page.trending.TrendingComponent
import com.nmt.kmpwallpaper.util.StringProvider
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HomeComponent(
    componentContext: ComponentContext,
    private val imageRepository: ImageRepository,
    private val changeLanguageUseCase: ChangeLanguageUseCase,
    private val dataStore: DataStore<Preferences>,
) : ScreenComponent,
    KoinComponent,
    ComponentContext by componentContext {
    private val _uiState = MutableValue(HomeUiState())
    val uiState: Value<HomeUiState> = _uiState

    private val _recentList = MutableValue<List<Photo>>(listOf())
    val recentList = _recentList
    private val firebaseDatabase by inject<FirebaseDatabase>()
    private var query: String = ""
    private val scope = coroutineScope()
    private val imageJob = coroutineScope()

    private val pageNavigation = PagesNavigation<PageConfiguration>()
    val trendingImages = imageRepository.pagingTrendingData.cachedIn(scope)
    val newsImages = imageRepository.pagingNewsData.value.cachedIn(scope)
    val page =
        childPages(
            source = pageNavigation,
            serializer = PageConfiguration.serializer(),
            initialPages = {
                Pages(
                    items =
                        listOf(
                            PageConfiguration.Trending,
                            PageConfiguration.Recent,
                            PageConfiguration.New,
                        ),
                    selectedIndex = 0,
                )
            },
            handleBackButton = true,
        ) { configuration, componentContext ->
            when (configuration) {
                PageConfiguration.Trending -> {
                    Page.Trending(
                        TrendingComponent(
                            componentContext,
                        ),
                    )
                }
                PageConfiguration.Recent -> {
                    Page.Recent(
                        RecentComponent(
                            componentContext,
                        ),
                    )
                }
                PageConfiguration.New -> {
                    Page.New(
                        NewComponent(
                            componentContext,
                        ),
                    )
                }
            }
        }

    fun selectPage(
        index: Int,
        onComplete: () -> Unit = {},
    ) {
        pageNavigation.select(index = index, onComplete = { _, _ ->
            onComplete()
        })
    }

    init {
        updateUIFromLanguageProvider()
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
                    query = query.replace("$it ", "")
                } else {
                    query += "$it "
                }
                getPhotos()
            }
        }
    }

    fun onCategorySheetDismiss(list: List<Photo>) {
        scope.launch {
            if (list.isNotEmpty()) {
                list.forEach {
                    it.name?.let { nonNullName ->
                        if (query.contains(nonNullName)) {
                            query = query.replace("$nonNullName ", "")
                        } else {
                            query += "$nonNullName "
                        }
                    }
                }
                getPhotos()
            }
        }
    }

    fun onTrendingPhotoClicked(photo: Photo) {
        _recentList.update { value ->
            value.contains(photo).let {
                if (it) {
                    value
                } else {
                    val list = value.toMutableList()
                    list.add(photo)
                    list
                }
            }
        }
    }

    suspend fun onChangeLanguage(language: Language) =
        suspendCoroutine { continuation ->
            scope.launch {
                val allString = StringProvider.getAllStringMap().map { it.value }.toTypedArray()
                val map = StringProvider.getAllStringMap().toMutableMap()
                changeLanguageUseCase(
                    strings = allString,
                    from = LanguageProvider.getLocaleLanguage().code,
                    to = language.code,
                )?.let { nonNullResult ->
                    val newMap = map.keys.zip(nonNullResult).toMap()
                    StringProvider.updateAllStringByLanguage(map = newMap, language = language.code, dataStore = dataStore)
                    updateUIFromLanguageProvider()
                    LanguageProvider.changeCurrentLanguage(language)
                    continuation.resume(true)
                }
            }
        }

    private suspend fun getCategories() {
        firebaseDatabase.reference("Category").valueEvents.collect {
            val snapshot = it.value<List<CategoryModel>>()
            println("Check cate:$snapshot")
            _uiState.update { state ->
                state.copy(
                    categories = snapshot.map { model -> model.toPhoto() },
                )
            }
        }
    }

    private fun getPhotos() {
        imageRepository.query(query = query.takeIf { it != "" })
    }

    private fun updateUIFromLanguageProvider() {
        _uiState.update {
            it.copy(
                ui =
                    HomeUi(
                        apply = StringProvider.apply,
                        youHaveNotView = StringProvider.youHaveNotView,
                        setting = StringProvider.setting,
                        category = StringProvider.category,
                        viewAll = StringProvider.viewAll,
                        subCategories =
                            listOf(
                                SubCategory.Trending(
                                    StringProvider.trending,
                                ),
                                SubCategory.Recent(
                                    StringProvider.recent,
                                ),
                                SubCategory.New(
                                    StringProvider.new,
                                ),
                            ),
                        appSettings =
                            listOf(
                                AppSetting.Language(
                                    StringProvider.language,
                                ),
                                AppSetting.Rating(
                                    StringProvider.rating,
                                ),
                                AppSetting.TermNCondition(
                                    StringProvider.termNCondition,
                                ),
                                AppSetting.Privacy(
                                    StringProvider.privacy,
                                ),
                            ),
                    ),
            )
        }
    }
}
