package com.nmt.kmpwallpaper.network.di

import com.nmt.kmpcore.network.builder.BaseHttpClientBuilder
import com.nmt.kmpwallpaper.network.AppDataSource
import com.nmt.kmpwallpaper.network.AppSourceApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.Headers
import io.ktor.http.URLBuilder
import io.ktor.http.headersOf
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun provideAppNetworkModule() = module {
    single<HttpClient> {
        BaseHttpClientBuilder()
            .host("api.pexels.com/v1")
            .build(
                headers = Headers.build {
                    headersOf("Authorization","tJpnAKL7IAJlE9lS6P7cjMtDRnMyz0Bltvj2R00qo5pNMlFB610qvfLw")
                }
            )
    }

    single<AppSourceApi> {
        AppDataSource(
            get()
        )
    }
}