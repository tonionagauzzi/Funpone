package com.vitantonio.nagauzzi.funpone.data.entity

data class Link(val label: String = "", val url: String = "", val iconUri: String = "") {
    val isValid: Boolean
        get() = (url.startsWith("http://") || url.startsWith("https://"))
                && iconUri.startsWith("content://")
}
