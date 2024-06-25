package com.vitantonio.nagauzzi.funpone.data.repository

import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.vitantonio.nagauzzi.funpone.data.datasource.dataStore
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.test.rule.CoroutineRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingRepositoryTest {
    @get:Rule
    val coroutineRule = CoroutineRule()

    private val context = InstrumentationRegistry.getInstrumentation().context
    private val settingRepository: SettingRepository by context.repository()

    @Test
    fun test_set() = coroutineRule.runTest {
        val link = Link(
            label = "サンプル",
            url = "https://example.co.jp/"
        )
        settingRepository.link.test {
            awaitItem() // Skip an initial value
            settingRepository.set(link)
            assertEquals(link, awaitItem())
        }
    }

    @Test
    fun test_save() = coroutineRule.runTest {
        val link = Link(
            label = "サンプル",
            url = "https://example.co.jp/"
        )
        val link2 = Link(
            label = "サンプル2",
            url = "https://example2.co.jp/"
        )
        settingRepository.save(link)
        context.dataStore.data.test {
            assertEquals(link, awaitItem().toLink())
        }
        settingRepository.save(link2)
        context.dataStore.data.test {
            assertEquals(link2, awaitItem().toLink())
        }
    }
}
