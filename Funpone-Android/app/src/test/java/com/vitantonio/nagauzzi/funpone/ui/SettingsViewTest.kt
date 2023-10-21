package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.vitantonio.nagauzzi.funpone.data.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingsViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val settingsRepository = SettingsRepositoryStubForTest()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SettingsView(
                repository = settingsRepository,
                selectedUrl = "https://example.com"
            )
        }
    }

    @Test
    fun test_SettingsView_AddButton() = runBlocking {
        val node = composeTestRule.onNodeWithContentDescription("Add")
        node.assertIsDisplayed()
        node.assertHasClickAction()
        node.performClick()
        assertEquals(1, settingsRepository.saveUrlsCount)
        assertEquals(0, settingsRepository.saveSelectedUrlCount)

    }
}

private class SettingsRepositoryStubForTest(
    override val urls: Flow<List<String>> = listOf(listOf("https://example.com/")).asFlow(),
    override val selectedUrl: Flow<String> = listOf("https://example.com/").asFlow()
) : SettingsRepository {
    var saveUrlsCount = 0
    var saveSelectedUrlCount = 0

    override suspend fun save(urls: List<String>) {
        saveUrlsCount++
    }

    override suspend fun save(selectedUrl: String) {
        saveSelectedUrlCount++
    }

    override suspend fun save(urls: List<String>, selectedUrl: String) {
        saveUrlsCount++
        saveSelectedUrlCount++
    }
}
