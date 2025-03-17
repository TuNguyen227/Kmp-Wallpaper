package com.nmt.kmpwallpaper.network

import io.ktor.client.statement.HttpResponse

interface AppSourceApi {
    suspend fun search(
        query: String,
        page: String
    ) : HttpResponse
}