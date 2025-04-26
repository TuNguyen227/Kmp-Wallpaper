package com.nmt.kmpwallpaper.util

import org.koin.core.component.getScopeName

object StringProvider {
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

    private val map = mutableMapOf<String, String>()

    init {
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
        }
    }

    fun getAllString(): Array<String> {
        return map.map { it.value }.toList().toTypedArray()
    }

    fun updateAllString(map: Map<String, String>) {
        map.forEach { input ->
            this.map.forEach { entry ->
                if (input.key == entry.key) {
                    this.map[input.key] = entry.value
                    updateString(input.key)
                }
            }
        }
    }

    private fun updateString(input: String) {
        when (input) {
            ::category.name -> {
                category = input
            }

            ::viewAll.name -> {
                viewAll = input
            }

            ::trending.name -> {
                trending = input
            }

            ::recent.name -> {
                recent = input
            }

            ::new.name -> {
                new = input
            }

            ::setting.name -> {
                setting = input
            }

            ::language.name -> {
                language = input
            }

            ::rating.name -> {
                rating = input
            }

            ::termNCondition.name -> {
                termNCondition = input
            }

            ::actionSetHome.name -> {
                actionSetHome = input
            }

            ::actionSetLock.name -> {
                actionSetLock = input
            }

            ::actionSetBoth.name -> {
                actionSetBoth = input
            }
        }
    }
}