package com.vitantonio.nagauzzi.funpone.data.repository

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface Repository

internal inline fun <reified T : Repository> Context.repository(
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): Lazy<T> = lazy {
    when (T::class) {
        IconRepository::class -> IconRepositoryImpl(context = this, dispatcher = ioDispatcher) as T
        SettingRepository::class -> SettingRepositoryImpl(context = this, dispatcher = ioDispatcher) as T
        ShortcutRepository::class -> ShortcutRepositoryImpl(context = this) as T
        else -> throw IllegalArgumentException("Unknown repository type: ${T::class}")
    }
}
