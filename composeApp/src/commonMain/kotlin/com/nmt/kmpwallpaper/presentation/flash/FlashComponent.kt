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
import com.nmt.kmpwallpaper.network.APIHandler
import com.nmt.kmpwallpaper.network.AdsManager
import com.nmt.kmpwallpaper.presentation.ChildConfiguration
import com.nmt.kmpwallpaper.util.StringProvider
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class FlashComponent(
    componentContext: ComponentContext,
    private val dataStore: DataStore<Preferences>,
) : KoinComponent,
    ComponentContext by componentContext {
    private val _uiState = MutableValue(FlashUiState())
    val uiState: Value<FlashUiState> = _uiState

    private val _nativeAdId = MutableValue<String>("")

    val nativeAdId: Value<String> = _nativeAdId
    private val firebaseDatabase by inject<FirebaseDatabase>()
    private val scope = coroutineScope()

    init {
        scope.launch {
            _uiState.update {
                it.copy(
                    description = "Colorful your world!",
                )
            }
            dataStore.data.take(1).collect { data ->
                val allKey = StringProvider.getAllStringMap().map { it.key }
                val map = StringProvider.getAllStringMap().toMutableMap()

                allKey.forEach { key ->
                    data[stringPreferencesKey(key)]?.let {
                        map[key] = it
                    }
                }

                val language = data[stringPreferencesKey(StringProvider::currentLanguage.name)] ?: "en"
                StringProvider.updateAllStringByLanguage(map = map, dataStore = dataStore, language = language)
                LanguageProvider.changeCurrentLanguage(Language.fromCode(language) ?: Language.English)
                coroutineScope().launch {
                    AdsManager.load()
                    AdsManager.nativeid?.let { id ->
                        _nativeAdId.update {
                            id
                        }
                    }
                }
                firebaseDatabase.reference("Api").valueEvents.collect { it ->
                    val snapshot = it.value<List<String>>()
                    snapshot.randomOrNull()?.let { key ->
                        APIHandler.setKey(key)
                    }
                    _uiState.update {
                        it.copy(
                            navigateState = ChildConfiguration.Home,
                        )
                    }
                    this.cancel()
                }
            }
        }
    }
}
