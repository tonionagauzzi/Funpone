package com.vitantonio.nagauzzi.funpone.data.repository

import android.content.Context

interface Repository

internal inline fun <reified T : Repository> Context.repository(): Lazy<T> = lazy {
    when (T::class) {
        SettingRepository::class -> SettingRepositoryImpl(this) as T
        ShortcutRepository::class -> ShortcutRepositoryImpl(this) as T
        else -> throw IllegalArgumentException("Unknown repository type: ${T::class}")
    }
}
