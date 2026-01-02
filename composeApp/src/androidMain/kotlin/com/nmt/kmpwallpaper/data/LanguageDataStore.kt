package com.nmt.kmpwallpaper.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nmt.kmpwallpaper.data.dataStore.DATASTORE_FILE_NAME

fun createDataStore(context: Context) : DataStore<Preferences> {
    return com.nmt.kmpwallpaper.data.dataStore.createDataStore {
        context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
    }
}