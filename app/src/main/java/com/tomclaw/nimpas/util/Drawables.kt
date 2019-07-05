package com.tomclaw.nimpas.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable


fun Drawable.toBitmap(
        config: Bitmap.Config = Bitmap.Config.ARGB_8888,
        bitmapWidth: Int = intrinsicWidth,
        bitmapHeight: Int = intrinsicHeight
): Bitmap? {
    if (this is BitmapDrawable) {
        if (bitmap != null) {
            return bitmap
        }
    }
    val result = Bitmap.createBitmap(bitmapWidth, bitmapHeight, config)
    val canvas = Canvas(result)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    canvas.setBitmap(null)
    return result
}
