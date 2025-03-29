package com.nmt.kmpwallpaper.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class AppDataSource(
    private val client: HttpClient
) : AppSourceApi {
    override suspend fun search(query: String,page: String) : HttpResponse {
        return client.get {
            url {
                path("/v1/search")
                parameters.append("query",query)
                parameters.append("page",page)
                parameters.append("orientation","portrait")
                parameters.append("size","small")
            }
        }
    }
}