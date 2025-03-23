package com.nmt.kmpwallpaper.network.di

import com.nmt.kmpcore.network.builder.BaseHttpClientBuilder
import com.nmt.kmpwallpaper.network.AppDataSource
import com.nmt.kmpwallpaper.network.AppSourceApi
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import org.koin.dsl.module

fun provideAppNetworkModule() = module {
    single<HttpClient> {
        BaseHttpClientBuilder()
            .host("api.pexels.com/v1")
            .build(
                headers = {
                    header("Authorization","tJpnAKL7IAJlE9lS6P7cjMtDRnMyz0Bltvj2R00qo5pNMlFB610qvfLw")
                }
            )
    }

    single<AppSourceApi> {
        AppDataSource(
            get()
        )
    }
}