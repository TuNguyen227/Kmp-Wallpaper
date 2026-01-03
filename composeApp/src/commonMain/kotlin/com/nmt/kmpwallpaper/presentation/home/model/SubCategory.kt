package com.nmt.kmpwallpaper.presentation.home.model

import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_new
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_recent
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_trending
import org.jetbrains.compose.resources.DrawableResource

sealed class SubCategory(val name: String, val icon: DrawableResource) {
    data class Trending(
        val nameValue: String = "Trending",
        val iconValue: DrawableResource = Res.drawable.ic_trending
    ) : SubCategory(name = nameValue, icon = iconValue)

    data class Recent(
        val nameValue: String = "Recent",
        val iconValue: DrawableResource = Res.drawable.ic_recent
    ) : SubCategory(name = nameValue, icon = iconValue)

    data class New(
        val nameValue: String = "New",
        val iconValue: DrawableResource = Res.drawable.ic_new
    ) : SubCategory(name = nameValue, icon = iconValue)
}