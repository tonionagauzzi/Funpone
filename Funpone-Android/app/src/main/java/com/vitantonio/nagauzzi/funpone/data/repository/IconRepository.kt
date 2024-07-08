package com.vitantonio.nagauzzi.funpone.data.repository

import android.content.Context
import android.graphics.drawable.Icon
import com.vitantonio.nagauzzi.funpone.data.datasource.IconLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher

interface IconRepository : Repository {
    suspend fun get(iconUri: String): Icon
}

internal class IconRepositoryImpl(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher,
    private val dataSource: IconLocalDataSource = IconLocalDataSource(
        context = context,
        dispatcher = dispatcher
    )
) : IconRepository {
    override suspend fun get(iconUri: String) = dataSource.load(iconUri = iconUri)
}
