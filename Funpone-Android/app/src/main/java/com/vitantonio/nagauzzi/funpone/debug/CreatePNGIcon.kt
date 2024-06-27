package com.vitantonio.nagauzzi.funpone.debug

import android.graphics.Bitmap
import android.graphics.drawable.Icon

fun createPNGIcon(): Icon {
    val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    return Icon.createWithBitmap(bitmap)
}
