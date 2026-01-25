package com.nmt.kmpwallpaper.presentation.home.page.trending

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue

class TrendingComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {
    val scrollState = MutableValue(Pair(0, 0))
}
