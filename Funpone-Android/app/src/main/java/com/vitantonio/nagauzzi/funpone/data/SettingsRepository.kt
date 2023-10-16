package com.vitantonio.nagauzzi.funpone.data

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vitantonio.nagauzzi.funpone.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

interface SettingsRepository: Repository {
    val urls: Flow<List<String>>
    val selectedUrl: Flow<String>

    suspend fun save(urls: List<String>)
    suspend fun save(selectedUrl: String)
    suspend fun save(urls: List<String>, selectedUrl: String)
}

internal class SettingsRepositoryImpl(
    private val context: Context
): SettingsRepository {
    override val urls: Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.URLS]?.split("¥n")?.toList()
                ?: listOf(PreferencesValues.INITIAL_URL)
        }

    override val selectedUrl: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.SELECTED_URL] ?: PreferencesValues.INITIAL_URL
        }

    override suspend fun save(urls: List<String>) {
        context.dataStore.edit { settings ->
            settings.set(urls)
        }
    }

    override suspend fun save(selectedUrl: String) {
        context.dataStore.edit { settings ->
            settings.set(selectedUrl)
        }
    }

    override suspend fun save(urls: List<String>, selectedUrl: String) {
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

    private object PreferencesKeys {
        val URLS = stringPreferencesKey("urls")
        val SELECTED_URL = stringPreferencesKey("selected_url")
    }

    private object PreferencesValues {
        const val INITIAL_URL = "https://www.yahoo.co.jp/"
    }
}

class SettingsRepositoryStub(
    override val urls: Flow<List<String>> = listOf(listOf("https://example.com/")).asFlow(),
    override val selectedUrl: Flow<String> = listOf("https://example.com/").asFlow()
) : SettingsRepository {
    override suspend fun save(urls: List<String>) = Unit

    override suspend fun save(selectedUrl: String) = Unit

    override suspend fun save(urls: List<String>, selectedUrl: String) = Unit
}
