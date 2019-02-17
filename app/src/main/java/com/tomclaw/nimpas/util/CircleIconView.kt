package com.tomclaw.nimpas.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v7.content.res.AppCompatResources
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

    fun setIconColoredRes(svg: String, @ColorRes color: Int? = null, @ColorRes background: Int) {
        setIconColored(
                svg = svg,
                color = color?.let { resources.getColor(it) } ?: 0x000,
                background = resources.getColor(background)
        )
    }

    fun setIconColored(svg: String, @ColorInt color: Int = 0x000, @ColorInt background: Int) {
        val backDrawable = AppCompatResources.getDrawable(context, R.drawable.circle_back)
        if (backDrawable != null) {
            backDrawable.setColorFilter(background, PorterDuff.Mode.SRC_ATOP)
            circle?.setBackgroundDrawable(backDrawable)
        }
        val picture = SVG.getFromString(svg).renderToPicture()
        val bitmap = picture.toBitmap(
                bitmapWidth = dpToPx(picture.width, resources),
                bitmapHeight = dpToPx(picture.height, resources)
        )
        icon?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        icon?.setImageBitmap(bitmap)
    }

}
