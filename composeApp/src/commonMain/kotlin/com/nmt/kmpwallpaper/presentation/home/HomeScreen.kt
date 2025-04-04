package com.nmt.kmpwallpaper.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListPrefetchStrategy
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.pages.ChildPages
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.value.update
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_drawer
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_new
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_recent
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_trending
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_wallpaper
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.component.ButtonIcon
import com.nmt.kmpwallpaper.presentation.component.CardItem
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.DrawerBody
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.DrawerHeader
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.Settings
import com.nmt.kmpwallpaper.presentation.home.page.Page
import com.nmt.kmpwallpaper.presentation.home.page.trending.TrendingPage
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    component: HomeComponent,
    onPhotoClick:(Photo) -> Unit
) {
    val uiState by component.uiState.subscribeAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.75f),
                drawerContainerColor = Color(0xFFF9F9F9)
            ) {
                Column {
                    DrawerHeader()
                    DrawerBody(
                        items = listOf(
                            Settings.NOTIFICATIONS,
                            Settings.LANGUAGE,
                            Settings.RATING,
                            Settings.TERM_CONDITIONS,
                            Settings.PRIVACY
                        ),
                        onItemClick = {
                        },
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = {}, navigationIcon = {
                    Box(Modifier.fillMaxSize()) {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Icon(
                                imageVector = vectorResource(
                                    Res.drawable.ic_drawer
                                ),
                                contentDescription = "top bar drawer icon",
                                tint = Color.Unspecified
                            )
                        }

                        Icon(
                            imageVector = vectorResource(
                                Res.drawable.ic_wallpaper
                            ),
                            contentDescription = "top bar app icon",
                            tint = Color.Unspecified,
                            modifier = Modifier.align(Alignment.Center).size(48.dp)
                        )
                    }
                })
            }
        ) {
            HomeContent(
                homeComponent = component,
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                photos = uiState.images,
                categories = uiState.categories,
                onCategoryClick = component::onCategoryClick,
                onPhotoClick = onPhotoClick
            )
        }
    }
}

@Composable
private fun HomeContent(
    homeComponent: HomeComponent,
    modifier: Modifier = Modifier,
    photos: List<Photo>,
    categories: List<Photo>,
    onCategoryClick:(Photo) -> Unit,
    onPhotoClick: (Photo) -> Unit
) {
    println(
        "photos ${photos.size}"
    )
    val pages by homeComponent.page.subscribeAsState()
    val scrollState by homeComponent.scrollState.subscribeAsState()
    Column(
        modifier = modifier.fillMaxSize().padding(
            horizontal = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

//        val categoryHeader by animateDpAsState(
//            targetValue = if (photoScrollState.firstVisibleItemIndex > 2) 0.dp else 100.dp
//        )
        Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.semantics {
                        this.contentDescription = "Category text"
                    }.weight(1f)
                )

                Text(
                    text = "View all",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.semantics {
                        this.contentDescription = "ViewAll text"
                    }.align(Alignment.Bottom),
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            }

            LazyRow {
                items(
                    items = categories
                ) { item ->
                    CardItem(
                        modifier = Modifier.padding(end = 16.dp).wrapContentHeight(),
                        imageModifier = Modifier.size(100.dp),
                        onItemClick = {
                            onCategoryClick(item)
                        },
                        photo = item
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val scope = rememberCoroutineScope()
            SubCategoryItem(
                onItemClick = {
                    when(it) {
                        SubCategory.Trending -> {
                            scope.launch {
                                homeComponent.selectPage(index = 0)
                            }
                        }
                        SubCategory.Recent -> {
                            scope.launch {
                                homeComponent.selectPage(index = 1)
                            }
                        }
                        SubCategory.New -> {
                            scope.launch {
                                homeComponent.selectPage(index = 2)
                            }
                        }
                    }
                }
            )
            ChildPages(
                pages = pages,
                onPageSelected = homeComponent::selectPage
            ) { index, page ->
                when(page) {
                    is Page.Trending -> {
                        val scrollStateT by page.component.scrollState.subscribeAsState()

                        val state = rememberLazyListState(
                            initialFirstVisibleItemIndex = scrollStateT.first,
                            initialFirstVisibleItemScrollOffset = scrollStateT.second
                        )
                        println(
                         "state $scrollStateT ${state.firstVisibleItemIndex}"
                        )
                        TrendingPage(
                            photos = photos,
                            onLikeClick = {},
                            onItemClick = onPhotoClick,
                            state = state,
                            onScrollEnd = { itemIndex , offset ->
                                println(
                                    "state onScrollEnd $itemIndex $offset"
                                )
                                page.component.scrollState.update {
                                    itemIndex to offset
                                }
                            }
                        )
                    }
                    is Page.Recent -> {}
                    is Page.New-> {}
                }
            }
        }
    }
}

@Composable
private fun SubCategoryItem(
    onItemClick: (SubCategory) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier.background(
                color = Color(0xFFE1F4FF),
                shape = CircleShape
            ).padding(5.dp)
            ,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val list = listOf(
                SubCategory.Trending,
                SubCategory.Recent,
                SubCategory.New
            )
            var clickedSubCategory by rememberSaveable {
                mutableStateOf(SubCategory.Trending)
            }
            list.forEach {
                ButtonIcon(
                    modifier = Modifier.width(100.dp),
                    content = it.name,
                    color = CardDefaults.cardColors(
                        containerColor = if (it == clickedSubCategory) MaterialTheme.colorScheme.primaryContainer
                        else Color.Unspecified
                    ),
                    shape = CircleShape,
                    onClick = { clickedItem ->
                        SubCategory.fromValue(clickedItem)?.let { nonNullSubCategory ->
                            clickedSubCategory = nonNullSubCategory
                            onItemClick(nonNullSubCategory)
                        }
                    },
                    icon = it.icon,
                    iconColor = if (it == clickedSubCategory) Color.White
                    else Color.Unspecified
                )
            }
        }
    }
}

enum class SubCategory(val icon: DrawableResource) {
    Trending(Res.drawable.ic_trending),
    Recent(Res.drawable.ic_recent),
    New(Res.drawable.ic_new);
    companion object {
        fun fromValue(value: String) : SubCategory? {
            return enumValues<SubCategory>().find { it.name == value }
        }
    }
}