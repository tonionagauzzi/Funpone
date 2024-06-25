package com.vitantonio.nagauzzi.funpone.data.repository

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import androidx.core.net.toUri
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import java.util.UUID

interface ShortcutRepository : Repository {
    fun createShortcut(link: Link, icon: Icon)
}

internal class ShortcutRepositoryImpl(private val context: Context) : ShortcutRepository {
    override fun createShortcut(link: Link, icon: Icon) {
        val shortcutManager = context.getSystemService(ShortcutManager::class.java) ?: return
        if (shortcutManager.isRequestPinShortcutSupported) {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = link.url.toUri()
            }
            val shortcutInfo = ShortcutInfo.Builder(context, UUID.randomUUID().toString())
                .setShortLabel(link.label)
                .setIntent(intent)
                .setIcon(icon)
                .build()
            val shortcutResultIntent = shortcutManager.createShortcutResultIntent(shortcutInfo)
            val successCallback = PendingIntent.getBroadcast(
                context,
                0,
                shortcutResultIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            shortcutManager.requestPinShortcut(
                shortcutInfo,
                successCallback.intentSender
            )
        }
    }
}
