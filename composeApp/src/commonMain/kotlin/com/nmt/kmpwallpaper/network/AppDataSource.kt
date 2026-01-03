package com.nmt.kmpwallpaper.network

import com.nmt.kmpcore.infrastructure.provider.LanguageProvider
import com.nmt.kmpcore.network.builder.BaseHttpClientBuilder
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.path

class AppDataSource() : AppSourceApi {
    private val client: HttpClient = BaseHttpClientBuilder()
        .host("api.pexels.com")
        .build(
            headers = {
                header("Authorization","tJpnAKL7IAJlE9lS6P7cjMtDRnMyz0Bltvj2R00qo5pNMlFB610qvfLw")
            }
        )
    override suspend fun search(query: String,page: String) : HttpResponse {
        return client.get {
            url {
                path("/v1/search")
                parameters.append("query",query)
                parameters.append("page",page)
                parameters.append("orientation","portrait")
                parameters.append("size","small")
                parameters.append("per_page","16")
                parameters.append("locale",LanguageProvider.getLocaleLanguage().code)
            }
        }
    }

    override suspend fun getNews(page: String): HttpResponse {
        return client.get {
            url {
                path("/v1/curated")
                parameters.append("page",page)
                parameters.append("per_page","16")
                parameters.append("locale",LanguageProvider.getLocaleLanguage().code)
            }
        }
    }
}