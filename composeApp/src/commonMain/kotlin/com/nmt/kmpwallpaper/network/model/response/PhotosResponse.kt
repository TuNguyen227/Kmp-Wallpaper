package com.nmt.kmpwallpaper.network.model.response

import com.nmt.kmpwallpaper.model.Photo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("total_results") val totalResults: Int,
    val page: Int,
    @SerialName("per_page") val perPage: Int,
    val photos: List<PhotoResponse>,
    @SerialName("next_page") val nextPage: String? = null,
)

@Serializable
data class PhotoResponse(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    @SerialName("photographer_url") val photographerUrl: String,
    @SerialName("photographer_id") val photographerId: Long,
    @SerialName("avg_color") val avgColor: String,
    val src: PhotoSrc,
    val liked: Boolean,
    val alt: String,
) {
    fun toPhoto(): Photo =
        Photo(
            imageUrl = src.portrait,
            id = id,
        )
}

@Serializable
data class PhotoSrc(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String,
)
