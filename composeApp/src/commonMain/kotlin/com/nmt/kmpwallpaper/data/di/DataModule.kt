package com.nmt.kmpwallpaper.data.di

import com.nmt.kmpwallpaper.data.DefaultImageRepository
import com.nmt.kmpwallpaper.data.ImageRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import org.koin.dsl.module

fun provideDataModule() = module {
    single<ImageRepository> { DefaultImageRepository(get()) }
    single<FirebaseDatabase> { Firebase.database }
}