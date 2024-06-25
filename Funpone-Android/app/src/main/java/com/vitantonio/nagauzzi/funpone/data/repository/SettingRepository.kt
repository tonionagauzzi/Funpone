package com.vitantonio.nagauzzi.funpone.data.repository

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.data.datasource.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingRepository : Repository {
    val link: Flow<Link>
    suspend fun save(link: Link)
}

internal class SettingRepositoryImpl(
    private val context: Context
) : SettingRepository {
    override val link: Flow<Link> = context.dataStore.data
        .map { preferences ->
            Link(
                label = preferences[PreferencesKeys.LABEL] ?: PreferencesValues.INITIAL_LABEL,
                url = preferences[PreferencesKeys.URL] ?: PreferencesValues.INITIAL_URL
            )
        }

    override suspend fun save(link: Link) {
        context.dataStore.edit { settings ->
            settings.set(link = link)
        }
    }

    private fun MutablePreferences.set(link: Link) {
        this[PreferencesKeys.URL] = link.url
        this[PreferencesKeys.LABEL] = link.label
    }

    private object PreferencesKeys {
        val URL = stringPreferencesKey("url")
        val LABEL = stringPreferencesKey("label")
    }

    private object PreferencesValues {
        const val INITIAL_URL = "https://www.yahoo.co.jp/"
        const val INITIAL_LABEL = "Yahoo! JAPAN"
    }
}
