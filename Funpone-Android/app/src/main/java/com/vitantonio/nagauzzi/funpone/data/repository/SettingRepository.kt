package com.vitantonio.nagauzzi.funpone.data.repository

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.data.datasource.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

interface SettingRepository : Repository {
    val link: StateFlow<Link>
    suspend fun set(link: Link)
    suspend fun save(link: Link)
}

internal class SettingRepositoryImpl(
    private val context: Context
) : SettingRepository {
    private val mutableLink = MutableStateFlow(Link())
    override val link: StateFlow<Link> = mutableLink.asStateFlow()

    init {
        context.dataStore.data.map { preferences ->
            set(preferences.toLink())
        }.launchIn(CoroutineScope(context.mainExecutor.asCoroutineDispatcher()))
    }

    override suspend fun set(link: Link) {
        mutableLink.emit(link)
    }

    override suspend fun save(link: Link) {
        context.dataStore.edit { settings ->
            settings.save(link = link)
        }
    }
}

private object PreferencesKeys {
    val URL = stringPreferencesKey("url")
    val LABEL = stringPreferencesKey("label")
}

private object PreferencesValues {
    const val INITIAL_URL = "https://www.yahoo.co.jp/"
    const val INITIAL_LABEL = "Yahoo! JAPAN"
}

private fun MutablePreferences.save(link: Link) {
    this[PreferencesKeys.URL] = link.url
    this[PreferencesKeys.LABEL] = link.label
}

@VisibleForTesting(otherwise = PRIVATE)
fun Preferences.toLink(): Link {
    return Link(
        label = this[PreferencesKeys.LABEL] ?: PreferencesValues.INITIAL_LABEL,
        url = this[PreferencesKeys.URL] ?: PreferencesValues.INITIAL_URL
    )
}
