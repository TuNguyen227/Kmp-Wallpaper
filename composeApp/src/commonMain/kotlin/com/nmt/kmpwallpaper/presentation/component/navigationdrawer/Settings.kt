package com.nmt.kmpwallpaper.presentation.component.navigationdrawer

import com.nmt.kmpwallpaper.composeApp.commonMain.Res
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_device_home
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_device_home_n_lock
import com.nmt.kmpwallpaper.composeApp.commonMain.ic_device_lock
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
    PRIVACY("Privacy Policy", Res.drawable.ic_privacy),

    ACTION_SET_HOME_SCREEN("Set as Home Screen",Res.drawable.ic_device_home),
    ACTION_SET_LOCK_SCREEN("Set as Lock Screen",Res.drawable.ic_device_lock),
    ACTION_SET_HOME_N_LOCK("Set as both screens",Res.drawable.ic_device_home_n_lock)
}