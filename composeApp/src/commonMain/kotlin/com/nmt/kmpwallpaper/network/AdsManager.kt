package com.nmt.kmpwallpaper.network

import com.nmt.kmpwallpaper.network.model.response.Ads
import dev.gitlive.firebase.database.FirebaseDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

object AdsManager : KoinComponent {
    private val firebaseDatabase by inject<FirebaseDatabase>()
    var interstitialid: String? = null
        private set
    var bannerid: String? = null
        private set
    var nativeid: String? = null
        private set

    suspend fun load() {
        firebaseDatabase.reference("ads").valueEvents.collect {
            val snapshot = it.value<Ads>()
            println("Check snapshot: $snapshot")
            snapshot.let { data ->
                this.interstitialid = data.interstitialid
                this.bannerid = data.bannerid
                this.nativeid = data.nativeid
            }
        }
    }
}
