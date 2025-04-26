package com.nmt.kmpwallpaper.model

import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_device_home
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_device_home_n_lock
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_device_lock
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_language
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_privacy
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_rating
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_term_conditions
import org.jetbrains.compose.resources.DrawableResource

sealed class AppSetting(val name:String, val icon: DrawableResource) {
    data class Language(
        val nameValue: String = "Language",
        val iconValue: DrawableResource = Res.drawable.ic_language
    ) : AppSetting(name = nameValue, icon = iconValue)
    data class Rating(
        val nameValue: String = "Rate this app",
        val iconValue: DrawableResource = Res.drawable.ic_rating
    ) : AppSetting(name = nameValue, icon = iconValue)
    data class TermNCondition(
        val nameValue: String = "Term & Conditions",
        val iconValue: DrawableResource = Res.drawable.ic_term_conditions
    ) : AppSetting(name = nameValue, icon = iconValue)
    data class Privacy(
        val nameValue: String = "Privacy Policy",
        val iconValue: DrawableResource = Res.drawable.ic_privacy
    ) : AppSetting(name = nameValue, icon = iconValue)

    data class ActionSetAsHome(
        val nameValue: String = "Set as Home Screen",
        val iconValue: DrawableResource = Res.drawable.ic_device_home
    ) : AppSetting(name = nameValue, icon = iconValue)

    data class ActionSetAsLock(
        val nameValue: String = "Set as Lock Screen",
        val iconValue: DrawableResource = Res.drawable.ic_device_lock
    ) : AppSetting(name = nameValue, icon = iconValue)

    data class ActionSetBoth(
        val nameValue: String = "Set as both screens",
        val iconValue: DrawableResource = Res.drawable.ic_device_home_n_lock
    ) : AppSetting(name = nameValue, icon = iconValue)
}