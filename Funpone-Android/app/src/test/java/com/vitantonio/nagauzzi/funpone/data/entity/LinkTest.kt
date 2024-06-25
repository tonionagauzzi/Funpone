package com.vitantonio.nagauzzi.funpone.data.entity

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LinkTest {
    @Test
    fun test_isValid() {
        assertTrue(Link(label = "", url = "http://example.com/").isValid)
        assertTrue(Link(label = "", url = "https://example.com/").isValid)
        assertFalse(Link(label = "", url = "example.com").isValid)
        assertFalse(Link(label = "", url = "mailto:user@example.com").isValid)
    }
}
