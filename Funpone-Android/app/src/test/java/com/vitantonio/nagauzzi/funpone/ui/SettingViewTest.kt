package com.vitantonio.nagauzzi.funpone.ui

import android.content.pm.ShortcutManager
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.core.net.toUri
import androidx.test.platform.app.InstrumentationRegistry
import com.vitantonio.nagauzzi.funpone.data.datasource.dataStore
import com.vitantonio.nagauzzi.funpone.data.entity.Link
import com.vitantonio.nagauzzi.funpone.data.repository.SettingRepository
import com.vitantonio.nagauzzi.funpone.data.repository.ShortcutRepository
import com.vitantonio.nagauzzi.funpone.data.repository.repository
import com.vitantonio.nagauzzi.funpone.data.repository.toLink
import com.vitantonio.nagauzzi.funpone.test.rule.CoroutineRule
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingViewTest {
    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().context
    private val settingRepository: SettingRepository by context.repository()

    @Before
    fun setUp() {
        val shortcutRepository: ShortcutRepository by context.repository()
        composeTestRule.setContent {
            SettingView(
                coroutineScope = coroutineRule.scope,
                settingRepository = settingRepository,
                shortcutRepository = shortcutRepository
            )
        }
    }

    @Test
    fun test_SettingView_addLink() = coroutineRule.runTest {
        // Input the label
        val labelNode = composeTestRule.onNodeWithTag("LinkLabel")
        labelNode.assertIsDisplayed()
        labelNode.performTextClearance()
        labelNode.performTextInput("Example2")

        // Input the URL
        val urlNode = composeTestRule.onNodeWithTag("LinkUrl")
        urlNode.assertIsDisplayed()
        urlNode.performTextClearance()
        urlNode.performTextInput("https://example2.com/")

        // Check set link
        val expectedLink = Link(
            label = "Example2",
            url = "https://example2.com/"
        )
        assertEquals(expectedLink, settingRepository.link.value)

        // Click the Add button
        val node = composeTestRule.onNodeWithContentDescription("Add")
        node.assertIsDisplayed()
        node.assertHasClickAction()
        node.performClick()

        // Check saved link
        val shortcutManager = context.getSystemService(ShortcutManager::class.java)!!
        val createdShortcut =
            shortcutManager.pinnedShortcuts.first { it.shortLabel == expectedLink.label }
        assertEquals(expectedLink.url.toUri(), createdShortcut.intent?.data)
        val savedLink = context.dataStore.data.first { it.toLink() == expectedLink }.toLink()
        assertEquals(expectedLink, savedLink)
    }
}
