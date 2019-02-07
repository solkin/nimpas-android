package com.tomclaw.nimpas.util

import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.ColorInt
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
        icon = findViewById(R.id.icon)
    }

    fun setIcon(svg: String, @ColorInt color: Int? = null, background: Int) {
        val backDrawable = AppCompatResources.getDrawable(context, R.drawable.circle_back)
        if (backDrawable != null) {
            backDrawable.setColorFilter(background, PorterDuff.Mode.SRC_ATOP)
            circle?.setBackgroundDrawable(backDrawable)
        }
        val bitmap = SVG.getFromString(svg)
                .renderToPicture()
                .toBitmap()
                .apply { if (color != null) tint(color) }
        icon?.setImageBitmap(bitmap)
    }

}
