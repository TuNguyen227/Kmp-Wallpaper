package com.nmt.kmpwallpaper.presentation.flash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.nmt.kmpwallpaper.presentation.ChildConfiguration
import com.nmt.kmpwallpaper.presentation.ScreenComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FlashComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext  {
    private val _uiState = MutableValue(FlashUiState())
    val uiState : Value<FlashUiState> = _uiState

    private val scope = coroutineScope()

    init {
        scope.launch {
            _uiState.update {
                println(
                    "Init"
                )
                it.copy(
                    description = "Colorful your world!"
                )
            }
            delay(2000L)
            _uiState.update {
                it.copy(
                    navigateState = ChildConfiguration.Home
                )
            }
        }
    }
}