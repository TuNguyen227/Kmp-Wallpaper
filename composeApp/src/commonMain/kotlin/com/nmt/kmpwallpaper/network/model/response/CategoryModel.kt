package com.nmt.kmpwallpaper.network.model.response

import com.nmt.kmpwallpaper.model.Photo
import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val name: String,
    val url: String,
) {
    fun toPhoto(): Photo =
        Photo(
            name = name,
            imageUrl = url,
        )
}
