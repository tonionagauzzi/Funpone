package com.vitantonio.nagauzzi.funpone.data.entity

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class LinkTest {
    @Test
    fun test_isValid_true() {
        assertTrue(
            Link(
                label = "",
                url = "http://example.com/",
                iconUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000000"
            ).isValid
        )
        assertTrue(
            Link(
                label = "",
                url = "https://example.com/",
                iconUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000000"
            ).isValid
        )
    }

    @Test
    fun test_isValid_iconUri() {
        assertFalse(
            Link(
                label = "",
                url = "example.com",
                iconUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000000"
            ).isValid
        )
        assertFalse(
            Link(
                label = "",
                url = "mailto:user@example.com",
                iconUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000000000"
            ).isValid
        )
        assertFalse(
            Link(
                label = "",
                url = "https://example.com/",
                iconUri = "example.com"
            ).isValid
        )
    }
}
