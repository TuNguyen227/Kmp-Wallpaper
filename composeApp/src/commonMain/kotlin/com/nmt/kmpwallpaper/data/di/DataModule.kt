package com.nmt.kmpwallpaper.data.di

import com.nmt.kmpwallpaper.data.DefaultImageRepository
import com.nmt.kmpwallpaper.data.ImageRepository
import org.koin.dsl.module

fun provideDataModule() = module {
    single<ImageRepository> { DefaultImageRepository(get()) }
}