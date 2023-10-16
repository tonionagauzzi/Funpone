package com.vitantonio.nagauzzi.funpone.data

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vitantonio.nagauzzi.funpone.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val context: Context
) {
    private object PreferencesKeys {
        val URLS = stringPreferencesKey("urls")
        val SELECTED_URL = stringPreferencesKey("selected_url")
    }

    private object PreferencesValues {
        const val INITIAL_URL = "https://www.yahoo.co.jp/"
    }

    val urls: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.URLS]?.split("¥n")?.toList()
                ?: listOf(PreferencesValues.INITIAL_URL)
        }

    val selectedUrl: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.SELECTED_URL] ?: PreferencesValues.INITIAL_URL
        }

    suspend fun save(urls: List<String>) {
        context.dataStore.edit { settings ->
            settings.set(urls)
        }
    }

    suspend fun save(selectedUrl: String) {
        context.dataStore.edit { settings ->
            settings.set(selectedUrl)
        }
    }

    suspend fun save(urls: List<String>, selectedUrl: String) {
        context.dataStore.edit { settings ->
            settings.set(urls)
            settings.set(selectedUrl)
        }
    }

    private fun MutablePreferences.set(urls: List<String>) {
        this[PreferencesKeys.URLS] = urls.joinToString(separator = "¥n")
    }

    private fun MutablePreferences.set(selectedUrl: String) {
        this[PreferencesKeys.SELECTED_URL] = selectedUrl
    }
}
