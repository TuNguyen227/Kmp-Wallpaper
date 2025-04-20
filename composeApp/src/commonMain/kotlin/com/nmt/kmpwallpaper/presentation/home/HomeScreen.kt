package com.nmt.kmpwallpaper.presentation.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_drawer
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_new
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_recent
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_trending
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_wallpaper
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.network.model.response.SearchResponse
import com.nmt.kmpwallpaper.presentation.component.ButtonIcon
import com.nmt.kmpwallpaper.presentation.component.CardItem
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.DrawerBody
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.DrawerHeader
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.Settings
import com.nmt.kmpwallpaper.presentation.home.page.trending.ImagePage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    component: HomeComponent,
    onPhotoClick: (Photo) -> Unit
) {
    val uiState by component.uiState.subscribeAsState()
    val images = component.images.collectAsLazyPagingItems()
    val recentImages by component.recentList.subscribeAsState()
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
                        IconButton(
                            onClick = {
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
        ) { value ->
            HomeContent(
                modifier = Modifier.padding(top = value.calculateTopPadding()),
                photos = images,
                categories = uiState.categories,
                onCategoryClick = component::onCategoryClick,
                recentImages = recentImages,
                onPhotoClick = { photo ->
                    onPhotoClick(photo)
                    component.onTrendingPhotoClicked(photo)
                },
                onCategorySheetDismiss = component::onCategorySheetDismiss,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    photos: LazyPagingItems<Photo>,
    categories: List<Photo>,
    recentImages: List<Photo>,
    onCategoryClick: (Photo) -> Unit,
    onCategorySheetDismiss: (List<Photo>) -> Unit,
    onPhotoClick: (Photo) -> Unit
) {
    val categoriesState = remember {
        mutableStateMapOf<String, Boolean>()
    }
    val viewAllSheet = remember {
        mutableStateOf(false)
    }
    if (categoriesState.isEmpty()) {
    categories.forEach {
        categoriesState[it.name ?: ""] = false
    }
    }
    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetPeekHeight = 0.dp,
        sheetContent = {
            CategorySheetView(
                isShow = viewAllSheet.value,
                onCategorySheetDismiss = { chosenCategories ->
                    viewAllSheet.value = false
                    chosenCategories.forEach {
                        val isItemClicked = categoriesState[it.name] ?: false
                        categoriesState[it.name ?: ""] = !isItemClicked
                    }
                    onCategorySheetDismiss(chosenCategories)
                },
                categories = categories,
                state = categoriesState
            )
        },
        content = {
            Column(
                modifier = modifier.fillMaxSize().padding(
                    horizontal = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val trendingState = rememberLazyGridState()
                val recentState = rememberLazyGridState()
                val newState = rememberLazyGridState()

                val categoryHeader by animateDpAsState(
                    targetValue = if (
                        trendingState.firstVisibleItemIndex > 2 ||
                        recentState.firstVisibleItemIndex > 2 ||
                        newState.firstVisibleItemIndex > 2
                    ) 0.dp else 100.dp
                )
                Column(modifier = Modifier.height(categoryHeader), verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                            }.align(Alignment.Bottom)
                                .clickable { viewAllSheet.value = true },
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                    LazyRow {
                        items(
                            items = categories
                        ) { item ->
                            val isItemClicked = categoriesState[item.name] ?: false
                            CardItem(
                                modifier = Modifier.width(125.dp).height(75.dp).padding(end = 16.dp)
                                    .wrapContentHeight(),
                                onItemClick = {
                                    onCategoryClick(item)
                                    categoriesState[it.name ?: ""] = !(categoriesState[it.name] ?: false)
                                },
                                photo = item,
                                borderStrokeEnabled = true,
                                isItemClicked = isItemClicked
                            )
                        }
                    }
                }
                val state = rememberPagerState { 3 }
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val scope = rememberCoroutineScope()
                    SubCategoryItem(
                        onItemClick = {
                            when (it) {
                                SubCategory.Trending -> {
                                    scope.launch {
                                        state.scrollToPage(0)
                                    }
                                }

                                SubCategory.Recent -> {
                                    scope.launch {
                                        state.scrollToPage(1)
                                    }
                                }

                                SubCategory.New -> {
                                    scope.launch {
                                        state.scrollToPage(2)
                                    }
                                }
                            }
                        }
                    )
                    HorizontalPager(
                        state = state
                    ) { page ->
                        when (page) {
                            0 -> {
                                Column {
                                    ImagePage(
                                        modifier = Modifier.fillMaxWidth().weight(1f),
                                        state = trendingState,
                                        lazyPhotos = photos,
                                        onItemClick = onPhotoClick
                                    )
                                    if (photos.loadState.append is LoadStateLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(50.dp).semantics { this.contentDescription = "Circle progress loading" }.align(Alignment.CenterHorizontally),
                                            color = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    }
                                }
                            }

                            1 -> {
                                ImagePage(
                                    modifier = Modifier.fillMaxWidth(),
                                    state = recentState,
                                    photos = recentImages,
                                    onItemClick = onPhotoClick
                                )
                            }

                            2 -> {

                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySheetView(
    isShow: Boolean,
    onCategorySheetDismiss: (List<Photo>) -> Unit,
    categories: List<Photo>,
    state: Map<String, Boolean>
) {
    if (isShow) {
        val scope = rememberCoroutineScope()
        val chosenCategories = rememberSaveable(categories.hashCode()) {
            mutableStateOf(mutableListOf<Photo>())
        }
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        ModalBottomSheet(
            sheetState = sheetState,
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            },
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }
                onCategorySheetDismiss(chosenCategories.value)
            },
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxHeight(0.9f).fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categories) { item ->
                        var isItemClicked by remember {
                            mutableStateOf(state[item.name] ?: false)
                        }
                        CardItem(
                            modifier = Modifier.height(100.dp),
                            photo = item,
                            onItemClick = {
                                chosenCategories.value.add(item)
                                isItemClicked = !isItemClicked
                            },
                            borderStrokeEnabled = true,
                            isItemClicked = isItemClicked
                        )
                    }
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledContentColor = Color.Unspecified,
                        disabledContainerColor = Color.Unspecified
                    ),
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                        }
                        onCategorySheetDismiss(chosenCategories.value)
                    }
                ) {
                    Text("Apply", style = MaterialTheme.typography.bodyMedium, color = Color.White)
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
            ).padding(5.dp),
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
        fun fromValue(value: String): SubCategory? {
            return enumValues<SubCategory>().find { it.name == value }
        }
    }
}