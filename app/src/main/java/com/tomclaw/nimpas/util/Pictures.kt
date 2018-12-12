package com.tomclaw.nimpas.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture

fun Picture.toBitmap(config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, config)
    val canvas = Canvas(bitmap)
    canvas.drawPicture(this)
    canvas.setBitmap(null)
    return bitmap
}