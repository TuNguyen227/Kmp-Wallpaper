package com.nmt.kmpwallpaper.di

import com.nmt.kmpcore.data.di.provideTranslateRepository
import com.nmt.kmpcore.domain.di.domainModule
import com.nmt.kmpcore.network.di.provideTranslationHttpClientModule
import com.nmt.kmpwallpaper.data.di.provideDataModule
import com.nmt.kmpwallpaper.network.di.provideNetworkModule
import io.ktor.client.request.header

val libraryModule =
    listOf(
        provideTranslationHttpClientModule(
            headers = {
                header("Ocp-Apim-Subscription-Key", "")
                header("Ocp-Apim-Subscription-Region", "eastus")
            },
        ),
        provideTranslateRepository,
        domainModule,
    )

val koinModules =
    listOf(
        provideNetworkModule(),
        provideDataModule(),
    ) + libraryModule
