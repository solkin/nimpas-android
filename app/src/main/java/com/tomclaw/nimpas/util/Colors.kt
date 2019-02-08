package com.tomclaw.nimpas.util

import android.support.annotation.ColorRes
import com.tomclaw.nimpas.R
import java.util.Random


data class ColorPair(
        @ColorRes val first: Int,
        @ColorRes val second: Int
)

private val COLORS = listOf(
        ColorPair(R.color.color_yellow, R.color.color_yellow_light),
        ColorPair(R.color.color_pink, R.color.color_pink_light),
        ColorPair(R.color.color_red, R.color.color_red_light),
        ColorPair(R.color.color_green, R.color.color_green_light),
        ColorPair(R.color.color_violet, R.color.color_violet_light),
        ColorPair(R.color.color_blue, R.color.color_blue_light),
        ColorPair(R.color.color_brown, R.color.color_brown_light)
)

fun randomColor(key: Long) = COLORS[Random(key).nextInt(COLORS.size)]