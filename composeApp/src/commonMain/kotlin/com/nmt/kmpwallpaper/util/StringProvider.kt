package com.nmt.kmpwallpaper.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

object StringProvider {
    var currentLanguage: String = "en"
        private set
    var category: String = "Category"
        private set
    var viewAll: String = "View all"
        private set
    var trending: String = "Trending"
        private set
    var recent: String = "Recent"
        private set
    var new: String = "New"
        private set
    var setting: String = "Setting"
        private set
    var language: String = "Language"
        private set
    var rating: String = "Rate this app"
        private set
    var termNCondition: String = "Term & Condition"
        private set
    var privacy: String = "Privacy"
        private set
    var actionSetHome: String = "Set as Home Screen"
        private set
    var actionSetLock: String = "Set as Lock Screen"
        private set
    var actionSetBoth: String = "Set as both screens"
        private set
    var youHaveNotView: String = "You have not viewed any images."
        private set
    var apply: String = "Apply"
        private set

    private val map = mutableMapOf<String, String>()
    init {
        println(
            "flow data String init"
        )
        with(map) {
            put(::category.name, category)
            put(::viewAll.name, viewAll)
            put(::trending.name, trending)
            put(::recent.name, recent)
            put(::new.name, new)
            put(::setting.name, setting)
            put(::language.name, language)
            put(::rating.name, rating)
            put(::termNCondition.name, termNCondition)
            put(::privacy.name, privacy)
            put(::actionSetHome.name, actionSetHome)
            put(::actionSetLock.name, actionSetLock)
            put(::actionSetBoth.name, actionSetBoth)
            put(::youHaveNotView.name, youHaveNotView)
            put(::apply.name, apply)
        }
    }

    fun getAllStringMap(): Map<String,String> {
        return map
    }

    suspend fun updateAllStringByLanguage(map: Map<String, String>, language: String, dataStore: DataStore<Preferences>) {
        map.forEach { input ->
            this.map.forEach { entry ->
                if (input.key == entry.key) {
                    this.map[input.key] = entry.value
                    updateString(input.key, input.value)
                    dataStore.edit {
                        it[stringPreferencesKey(input.key)] = input.value
                    }
                }
            }
        }
        currentLanguage = language
        dataStore.edit { it[stringPreferencesKey(::currentLanguage.name)] = language }
    }

    private fun updateString(input: String, newValue: String) {
        when (input) {
            ::category.name -> {
                category = newValue
            }

            ::viewAll.name -> {
                viewAll = newValue
            }

            ::trending.name -> {
                trending = newValue
            }

            ::recent.name -> {
                recent = newValue
            }

            ::new.name -> {
                new = newValue
            }

            ::setting.name -> {
                setting = newValue
            }

            ::language.name -> {
                language = newValue
            }

            ::rating.name -> {
                rating = newValue
            }

            ::privacy.name -> {
                privacy = newValue
            }

            ::termNCondition.name -> {
                termNCondition = newValue
            }

            ::actionSetHome.name -> {
                actionSetHome = newValue
            }

            ::actionSetLock.name -> {
                actionSetLock = newValue
            }

            ::actionSetBoth.name -> {
                actionSetBoth = newValue
            }

            ::youHaveNotView.name -> {
                youHaveNotView = newValue
            }
            ::apply.name -> {
                apply = newValue
            }
        }
    }
}