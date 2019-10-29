package com.tomclaw.nimpas.util

import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.Picture
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.caverock.androidsvg.SVG
import com.tomclaw.nimpas.R


class CircleIconView : FrameLayout {

    private var circle: View? = null
    private var icon: ImageView? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.circle_icon, this)
        circle = findViewById(R.id.circle)
        icon = findViewById(R.id.image)
    }

    fun setIconColoredRes(@DrawableRes icon: Int, @ColorRes color: Int? = null, @ColorRes background: Int) {
        setBackgroundColored(resources.getColor(background))
        setIconColored(
                drawable = resources.getDrawable(icon),
                color = color?.let { resources.getColor(it) } ?: TRANSPARENT
        )
    }

    fun setIconColoredRes(svg: String, @ColorRes color: Int? = null, @ColorRes background: Int) {
        val picture = SVG.getFromString(svg).renderToPicture()
        setBackgroundColored(resources.getColor(background))
        setIconColored(picture, color = color?.let { resources.getColor(it) } ?: TRANSPARENT)
    }

    fun setIconColored(svg: String, @ColorInt color: Int = TRANSPARENT, @ColorInt background: Int) {
        val picture = SVG.getFromString(svg).renderToPicture()
        setBackgroundColored(background)
        setIconColored(picture, color)
    }

    private fun setBackgroundColored(@ColorInt background: Int) {
        val backDrawable = AppCompatResources.getDrawable(context, R.drawable.circle_back)
        if (backDrawable != null) {
            backDrawable.setColorFilter(background, PorterDuff.Mode.SRC_ATOP)
            circle?.setBackgroundDrawable(backDrawable)
        }
    }

    private fun setIconColored(picture: Picture, @ColorInt color: Int = TRANSPARENT) {
        icon?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        val bitmap = picture.toBitmap(
                bitmapWidth = dpToPx(picture.width, resources),
                bitmapHeight = dpToPx(picture.height, resources)
        )
        icon?.setImageBitmap(bitmap)
    }

    private fun setIconColored(drawable: Drawable, @ColorInt color: Int = TRANSPARENT) {
        icon?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        val bitmap = drawable.toBitmap(
                bitmapWidth = dpToPx(drawable.intrinsicWidth, resources),
                bitmapHeight = dpToPx(drawable.intrinsicHeight, resources)
        )
        icon?.setImageBitmap(bitmap)
    }

}
