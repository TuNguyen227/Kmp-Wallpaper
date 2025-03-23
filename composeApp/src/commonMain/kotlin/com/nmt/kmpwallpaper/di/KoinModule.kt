package com.nmt.kmpwallpaper.di

import com.nmt.kmpcore.di.coreModule
import com.nmt.kmpwallpaper.data.di.provideDataModule
import com.nmt.kmpwallpaper.network.di.provideAppNetworkModule

val koinModules = listOf(
    provideAppNetworkModule(),
    provideDataModule()
) + coreModule