package com.nmt.kmpwallpaper.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nmt.kmpwallpaper.data.dataStore.DATASTORE_FILE_NAME
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
fun createDataStore(): DataStore<Preferences> {
    return com.nmt.kmpwallpaper.data.dataStore.createDataStore {
        val dir = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(dir).path() + "/$DATASTORE_FILE_NAME"
    }
}