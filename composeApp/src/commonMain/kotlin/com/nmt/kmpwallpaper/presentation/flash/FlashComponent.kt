package com.nmt.kmpwallpaper.presentation.flash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.nmt.kmpcore.infrastructure.provider.Language
import com.nmt.kmpcore.infrastructure.provider.LanguageProvider
import com.nmt.kmpwallpaper.presentation.ChildConfiguration
import com.nmt.kmpwallpaper.util.StringProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FlashComponent(
    componentContext: ComponentContext,
    private val dataStore : DataStore<Preferences>
) : ComponentContext by componentContext  {
    private val _uiState = MutableValue(FlashUiState())
    val uiState : Value<FlashUiState> = _uiState
    private val scope = coroutineScope()
    init {
        scope.launch {
            _uiState.update {
                it.copy(
                    description = "Colorful your world!"
                )
            }
            dataStore.data.take(1).collect { data ->
                println(
                    "flow data ${data.asMap()}"
                )
                val allKey = StringProvider.getAllStringMap().map { it.key }
                val map = StringProvider.getAllStringMap().toMutableMap()

                allKey.forEach { key ->
                    data[stringPreferencesKey(key)]?.let {
                        map[key] = it
                    }
                }

                val language = data[stringPreferencesKey(StringProvider::currentLanguage.name)] ?: "en"
                println(
                    "language $language"
                )
                StringProvider.updateAllStringByLanguage(map = map, dataStore = dataStore, language = language)
                LanguageProvider.changeCurrentLanguage(Language.fromCode(language) ?: Language.English)
                _uiState.update {
                    it.copy(
                        navigateState = ChildConfiguration.Home
                    )
                }
            }
        }
    }
}