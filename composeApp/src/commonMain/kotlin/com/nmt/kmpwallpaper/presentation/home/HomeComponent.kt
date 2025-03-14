package com.nmt.kmpwallpaper.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update

class HomeComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext  {
    private val uiState = MutableValue(HomeUiState())
    val _uiState : Value<HomeUiState> = uiState

    init {
        uiState.update {
            println(
                "Init"
            )
            it.copy(
                description = "Colorful your world!"
            )
        }
    }
}