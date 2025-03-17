package com.nmt.kmpwallpaper.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.nmt.kmpwallpaper.data.ImageRepository
import com.nmt.kmpwallpaper.presentation.ScreenComponent
import com.nmt.kmpwallpaper.presentation.flash.FlashUiState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HomeComponent(
    componentContext: ComponentContext,
    private val imageRepository: ImageRepository
) : ScreenComponent,IHomeComponent, KoinComponent, ComponentContext by componentContext  {
    private val _uiState = MutableValue(HomeUiState())
    val uiState : Value<HomeUiState> = _uiState
    private val scope = coroutineScope()
    init {
        scope.launch {
            imageRepository.search("trending","1")?.photos?.map {
                it.toPhoto()
            }?.let { photos ->
                _uiState.update {
                    it.copy(images = photos)
                }
            }
        }
    }

    override fun resetState() {

    }

    class Factory(
        private val imageRepository: ImageRepository
    ) : IHomeComponent.Factory {
        override fun invoke(componentContext: ComponentContext): HomeComponent {
            return HomeComponent(
                componentContext = componentContext,
                imageRepository = imageRepository
            )
        }
    }

}

interface IHomeComponent {
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
        ) : HomeComponent
    }
}