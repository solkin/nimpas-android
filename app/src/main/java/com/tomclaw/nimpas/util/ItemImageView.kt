package com.tomclaw.nimpas.util

import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.tomclaw.nimpas.R
import java.util.Random

class ItemImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    fun setItemId(itemId: Long) {
        val color = COLORS.getColor(itemId)

        val circleBackDrawable = AppCompatResources.getDrawable(context, R.drawable.circle_back)
        if (circleBackDrawable != null) {
            circleBackDrawable.setColorFilter(resources.getColor(color.second), MODE)
            setBackgroundDrawable(circleBackDrawable)
        }

        val avatarDrawable = drawable?.constantState?.newDrawable()?.mutate()
        if (avatarDrawable != null) {
            avatarDrawable.setColorFilter(resources.getColor(color.first), MODE)
            setImageDrawable(avatarDrawable)
        }
    }
}

internal data class ColorPair(
        @ColorRes val first: Int,
        @ColorRes val second: Int
)

private val MODE = PorterDuff.Mode.SRC_ATOP
private val COLORS = listOf(
        ColorPair(R.color.color_yellow, R.color.color_yellow_light),
        ColorPair(R.color.color_pink, R.color.color_pink_light),
        ColorPair(R.color.color_red, R.color.color_red_light),
        ColorPair(R.color.color_green, R.color.color_green_light),
        ColorPair(R.color.color_violet, R.color.color_violet_light),
        ColorPair(R.color.color_blue, R.color.color_blue_light),
        ColorPair(R.color.color_brown, R.color.color_brown_light)
)

private fun List<ColorPair>.getColor(id: Long) = this[Random(id).nextInt(size)]