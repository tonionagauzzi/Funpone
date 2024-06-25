package com.vitantonio.nagauzzi.funpone.ui

import android.graphics.drawable.Icon
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
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
class SettingViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val settingRepository = SettingRepositoryStubForTest()
    private val shortcutRepository = ShortcutRepositoryStubForTest()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SettingView(
                settingRepository = settingRepository,
                shortcutRepository = shortcutRepository
            )
        }
    }

    @Test
    fun test_SettingView_AddButton() = runBlocking {
        val node = composeTestRule.onNodeWithContentDescription("Add")
        node.assertIsDisplayed()
        node.assertHasClickAction()
        node.performClick()
        assertEquals(1, shortcutRepository.createShortcutCount)
    }
}

private class ShortcutRepositoryStubForTest : ShortcutRepository {
    var createShortcutCount = 0
    override fun createShortcut(link: Link, icon: Icon) {
        createShortcutCount++
    }
}

private class SettingRepositoryStubForTest(
    override val link: Flow<Link> = listOf(
        Link(
            label = "Example",
            url = "https://example.com/"
        )
    ).asFlow()
) : SettingRepository {
    var saveCount = 0
    override suspend fun save(link: Link) {
        saveCount++
    }
}
