package com.nmt.kmpwallpaper.di

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object KoinManager {
    var koin: Koin? = null

    fun initKoin(appDeclaration: KoinAppDeclaration? = null): Koin =
        startKoin {
            appDeclaration?.invoke(this)
            modules(koinModules)
        }.koin.apply {
            koin = this
        }
}
