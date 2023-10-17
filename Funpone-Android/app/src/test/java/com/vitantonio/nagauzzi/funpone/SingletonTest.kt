package com.vitantonio.nagauzzi.funpone

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SingletonTest {
    @Test
    fun test_isValid() {
        assertTrue(isValid("http://example.com/"))
        assertTrue(isValid("https://example.com/"))
        assertFalse(isValid("example.com"))
        assertFalse(isValid("mailto:user@example.com"))
    }
}
