package com.nmt.kmpwallpaper.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Ads(
    val bannerid: String?,
    val interstitialid: String?,
    val nativeid: String?,
)
