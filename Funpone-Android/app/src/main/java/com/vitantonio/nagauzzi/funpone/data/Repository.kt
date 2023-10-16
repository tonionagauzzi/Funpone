package com.vitantonio.nagauzzi.funpone.data

import android.content.Context

interface Repository

internal inline fun <reified T : Repository> Context.repository(): Lazy<T> = lazy {
    when (T::class) {
        SettingsRepository::class -> SettingsRepositoryImpl(this) as T
        else -> throw IllegalArgumentException("Unknown repository type: ${T::class}")
    }
}
