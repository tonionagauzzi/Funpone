package com.vitantonio.nagauzzi.funpone

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun isValid(url: String): Boolean {
    return url.startsWith("http://") || url.startsWith("https://")
}
