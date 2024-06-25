package com.vitantonio.nagauzzi.funpone.data.repository

import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingRepositoryTest {
    private val context = InstrumentationRegistry.getInstrumentation().context
    private val settingRepository: SettingRepository by context.repository()

    @Test
    fun test_save() = runBlocking {
        val link = Link(
            label = "サンプル",
            url = "https://example.co.jp/"
        )
        val link2 = Link(
            label = "サンプル2",
            url = "https://example2.co.jp/"
        )
        settingRepository.link.test {
            awaitItem() // Skip an initial value
            settingRepository.save(link)
            assertEquals(link, awaitItem())
            settingRepository.save(link2)
            assertEquals(link2, awaitItem())
        }
    }
}
