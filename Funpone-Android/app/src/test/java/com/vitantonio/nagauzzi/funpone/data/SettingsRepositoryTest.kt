package com.vitantonio.nagauzzi.funpone.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsRepositoryTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val settingsRepository: SettingsRepository by context.repository()

    @Test
    fun test_save() = runBlocking {
        val urls = listOf(
            "https://example.com/",
            "https://example2.co.jp/"
        )
        val urls2 = listOf(
            "https://example3.com/",
            "https://example4.co.jp/",
            "https://example5.co.jp/"
        )
        val selectedUrl = "https://example.com/"
        val selectedUrl2 = "https://example5.com/"

        settingsRepository.urls.test {
            awaitItem() // Skip an initial value
            settingsRepository.save(urls = urls)
            settingsRepository.save(urls = urls2, selectedUrl = selectedUrl2)
            assertEquals(urls, awaitItem())
            assertEquals(urls2, awaitItem())
        }

        settingsRepository.selectedUrl.test {
            awaitItem() // Skip an initial value
            settingsRepository.save(selectedUrl = selectedUrl)
            settingsRepository.save(selectedUrl = selectedUrl2, urls = urls2)
            assertEquals(selectedUrl, awaitItem())
            assertEquals(selectedUrl2, awaitItem())
        }
    }
}