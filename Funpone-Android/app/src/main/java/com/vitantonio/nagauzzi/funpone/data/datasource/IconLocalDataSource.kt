package com.vitantonio.nagauzzi.funpone.data.datasource

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException

internal class IconLocalDataSource(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun load(iconUri: String): Icon {
        return withContext(dispatcher) {
            try {
                val inputStream = context.contentResolver.openInputStream(iconUri.toUri())
                val iconBitmap = inputStream.use {
                    BitmapFactory.decodeStream(it)
                }
                Icon.createWithBitmap(iconBitmap)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
