package com.vitantonio.nagauzzi.funpone.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UrlItemDropdownMenuTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private var clickMoveAboveCount = 0
    private var clickMoveBelowCount = 0
    private var clickSelectCount = 0
    private var clickDeleteCount = 0
    private var dismissCount = 0

    @Before
    fun setUp() {
        composeTestRule.setContent {
            UrlItemDropdownMenu(
                expanded = true,
                onClickMoveAbove = { clickMoveAboveCount++ },
                onClickMoveBelow = { clickMoveBelowCount++ },
                onClickSelect = { clickSelectCount++ },
                onClickDelete = { clickDeleteCount++ },
                onDismiss = { dismissCount++ }
            )
        }
    }

    @Test
    fun test_UrlItemDropdownMenu_moveAbove() {
        composeTestRule.onNodeWithText("Move above")
            .assertIsDisplayed()
            .performClick()
        assertEquals(1, clickMoveAboveCount)
        assertEquals(0, clickMoveBelowCount)
        assertEquals(0, clickSelectCount)
        assertEquals(0, clickDeleteCount)
        assertEquals(1, dismissCount)
    }

    @Test
    fun test_UrlItemDropdownMenu_moveBelow() {
        composeTestRule.onNodeWithText("Move below")
            .assertIsDisplayed()
            .performClick()
        assertEquals(0, clickMoveAboveCount)
        assertEquals(1, clickMoveBelowCount)
        assertEquals(0, clickSelectCount)
        assertEquals(0, clickDeleteCount)
        assertEquals(1, dismissCount)
    }

    @Test
    fun test_UrlItemDropdownMenu_select() {
        composeTestRule.onNodeWithText("Select")
            .assertIsDisplayed()
            .performClick()
        assertEquals(0, clickMoveAboveCount)
        assertEquals(0, clickMoveBelowCount)
        assertEquals(1, clickSelectCount)
        assertEquals(0, clickDeleteCount)
        assertEquals(1, dismissCount)
    }

    @Test
    fun test_UrlItemDropdownMenu_delete() {
        composeTestRule.onNodeWithText("Delete")
            .assertIsDisplayed()
            .performClick()
        assertEquals(0, clickMoveAboveCount)
        assertEquals(0, clickMoveBelowCount)
        assertEquals(0, clickSelectCount)
        assertEquals(1, clickDeleteCount)
        assertEquals(1, dismissCount)
    }
}
