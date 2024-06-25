package com.vitantonio.nagauzzi.funpone.data.repository

import android.content.Context
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.io.FileOutputStream

@RunWith(RobolectricTestRunner::class)
class ShortcutRepositoryTest {
    private val context = InstrumentationRegistry.getInstrumentation().context
    private val shortcutManager = context.getSystemService(ShortcutManager::class.java)!!
    private val shortcutRepository: ShortcutRepository by context.repository()

    @Test
    fun test_createShortcut() {
        val link = Link(label = "サンプル", url = "https://example.co.jp/")
        val icon = createPNGIcon(context)
        shortcutRepository.createShortcut(link, icon)
        val createdShortcut = shortcutManager.pinnedShortcuts.first { it.shortLabel == link.label }
        assertEquals(link.url.toUri(), createdShortcut.intent?.data)
    }
}

private fun createPNGIcon(context: Context): Icon {
    val image = File("${context.cacheDir}/icon.png")
    FileOutputStream(image).use { outputStream ->
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
    }
    return Icon.createWithBitmap(BitmapFactory.decodeFile(image.path))
}
