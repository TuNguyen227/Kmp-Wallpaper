package com.nmt.kmpwallpaper.presentation.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_drawer
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_new
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_notification
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_privacy
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_rating
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_recent
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_term_conditions
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_trending
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_wallpaper
import com.nmt.kmpwallpaper.model.Photo
import com.nmt.kmpwallpaper.presentation.component.ButtonIcon
import com.nmt.kmpwallpaper.presentation.component.CardItem
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.DrawerBody
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.DrawerHeader
import com.nmt.kmpwallpaper.presentation.component.navigationdrawer.Settings
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    component: HomeComponent
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
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                photos = uiState.images
            )
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    photos: List<Photo>
) {
    Column(
        modifier = modifier.fillMaxSize().padding(
            horizontal = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val photoScrollState = rememberLazyGridState()
        val categoryHeader by animateDpAsState(
            targetValue = if (photoScrollState.firstVisibleItemIndex > 2) 0.dp else 100.dp
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
                    }.align(Alignment.Bottom),
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            }
            val list = listOf(
                Photo("cat1","https://picsum.photos/id/237/200/300"),
                Photo("cat2","https://picsum.photos/id/237/200/300"),
                Photo("cat3","https://picsum.photos/id/237/200/300"),
                Photo("cat4","https://picsum.photos/id/237/200/300"),
                Photo("cat5","https://picsum.photos/id/237/200/300"),
                Photo("cat6","https://picsum.photos/id/237/200/300")
            )

            LazyRow {
                items(
                    items = list
                ) {
                    CardItem(
                        modifier = Modifier.padding(end = 16.dp).wrapContentHeight(),
                        imageModifier = Modifier.size(100.dp),
                        onItemClick = {},
                        photo = it
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
            val scope = rememberCoroutineScope()
            SubCategoryItem(
                onItemClick = {
                    when(it) {
                        SubCategory.Trending -> {
                            scope.launch {
                                pagerState.scrollToPage(page = 0)
                            }
                        }
                        SubCategory.Recent -> {
                            scope.launch {
                                pagerState.scrollToPage(page = 1)
                            }
                        }
                        SubCategory.New -> {
                            scope.launch {
                                pagerState.scrollToPage(page = 2)
                            }
                        }
                    }
                }
            )

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) {
                when(pagerState.currentPage) {
                    0 -> {
                        LazyVerticalGrid(
                            state = photoScrollState,
                            columns = GridCells.Fixed(2),
                            content = {
                                items(
                                    items = photos
                                ) {
                                    CardItem(
                                        photo = it,
                                        onItemClick = {},
                                        isLikable = true,
                                        onLikeLick = {

                                        }
                                    )
                                }
                            },
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        )
                    }
                    1 -> {

                    }
                    2 -> {

                    }
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