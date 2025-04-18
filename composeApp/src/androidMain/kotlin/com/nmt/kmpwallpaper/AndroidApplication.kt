package com.nmt.kmpwallpaper

import android.app.Application
import com.nmt.kmpwallpaper.di.KoinManager

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinManager.initKoin {
            applicationContext
        }
    }
}