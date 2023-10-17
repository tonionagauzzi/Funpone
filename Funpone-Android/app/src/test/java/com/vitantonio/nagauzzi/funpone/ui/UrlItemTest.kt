package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UrlItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val url = "https://example.com"
    private var changedUrl = url
    private var urlChangeCount = 0
    private var clickDropdownCount = 0

    @Before
    fun setUp() {
        composeTestRule.setContent {
            UrlItem(
                selected = false,
                url = url,
                onUrlChange = { changedUrl ->
                    urlChangeCount++
                    this.changedUrl = changedUrl
                },
                onClickDropdown = { clickDropdownCount++ }
            )
        }
    }

    @Test
    fun test_UrlItem_Button() {
        val matcher = SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
        val node = composeTestRule.onNode(matcher = matcher)
        node.assertIsDisplayed()
        node.assertHasClickAction()
        node.performClick()
        assertEquals(0, urlChangeCount)
        assertEquals(1, clickDropdownCount)
    }

    @Test
    fun test_UrlItem_TextField() {
        composeTestRule.onNodeWithText(url)
            .assertIsDisplayed()
            .assertHasClickAction()
            .performTextReplacement("https://example.com/changed")
        assertEquals("https://example.com/changed", changedUrl)
        assertEquals(1, urlChangeCount)
        assertEquals(0, clickDropdownCount)
    }
}