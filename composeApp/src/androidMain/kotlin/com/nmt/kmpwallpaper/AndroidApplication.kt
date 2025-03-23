package com.nmt.kmpwallpaper

import android.app.Application
import com.nmt.kmpwallpaper.di.initKoin

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            applicationContext
        }
    }
}