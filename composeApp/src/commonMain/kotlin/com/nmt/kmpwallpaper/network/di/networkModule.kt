package com.nmt.kmpwallpaper.network.di

import com.nmt.kmpwallpaper.network.AppDataSource
import com.nmt.kmpwallpaper.network.AppSourceApi
import org.koin.dsl.module

fun provideNetworkModule() =
    module {
        single<AppSourceApi> { AppDataSource() }
    }
