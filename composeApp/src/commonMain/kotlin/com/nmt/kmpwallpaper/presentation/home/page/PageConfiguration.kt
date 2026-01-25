package com.nmt.kmpwallpaper.presentation.home.page

import com.nmt.kmpwallpaper.presentation.home.page.new.NewComponent
import com.nmt.kmpwallpaper.presentation.home.page.recent.RecentComponent
import com.nmt.kmpwallpaper.presentation.home.page.trending.TrendingComponent
import kotlinx.serialization.Serializable

@Serializable
sealed interface PageConfiguration {
    @Serializable
    data object Trending : PageConfiguration

    @Serializable
    data object Recent : PageConfiguration

    @Serializable
    data object New : PageConfiguration
}

sealed interface Page {
    data class Trending(
        val component: TrendingComponent,
    ) : Page

    data class Recent(
        val component: RecentComponent,
    ) : Page

    data class New(
        val component: NewComponent,
    ) : Page
}
