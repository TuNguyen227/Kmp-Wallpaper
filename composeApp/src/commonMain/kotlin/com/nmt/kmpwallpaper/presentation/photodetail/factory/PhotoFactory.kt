package com.nmt.kmpwallpaper.presentation.photodetail.factory

import com.arkivanov.mvikotlin.core.store.Store
import com.nmt.kmpwallpaper.model.Photo


interface PhotoFactory : Store<PhotoFactory.Intent, PhotoFactory.State, Nothing> {
    sealed class Intent {
        data class LoadPhotos(val page: Int) : Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val response: List<Photo> = emptyList()
    )
}