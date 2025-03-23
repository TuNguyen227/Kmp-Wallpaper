package com.nmt.kmpwallpaper.presentation.component.navigationdrawer

import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_language
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_notification
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_privacy
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_rating
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_term_conditions
import org.jetbrains.compose.resources.DrawableResource

enum class Settings(val title: String,val icon: DrawableResource){
    NOTIFICATIONS("Notifications",Res.drawable.ic_notification),
    LANGUAGE("Language",Res.drawable.ic_language),
    RATING("Rate this app",Res.drawable.ic_rating),
    TERM_CONDITIONS("Term & Conditions", Res.drawable.ic_term_conditions),
    PRIVACY("Privacy Policy", Res.drawable.ic_privacy)
}