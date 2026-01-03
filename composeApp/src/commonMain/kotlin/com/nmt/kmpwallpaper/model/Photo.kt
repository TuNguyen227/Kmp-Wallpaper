package com.nmt.kmpwallpaper.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val name: String? = null,
    val imageUrl : String,
    val originalUrl : String? = null,
    val id: Int? = null
)
