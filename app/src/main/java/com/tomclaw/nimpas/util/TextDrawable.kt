package com.tomclaw.nimpas.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape


interface ConfigBuilder {

    fun width(width: Int): ConfigBuilder

    fun height(height: Int): ConfigBuilder

    fun textColor(color: Int): ConfigBuilder

    fun withBorder(thickness: Int): ConfigBuilder

    fun useFont(font: Typeface): ConfigBuilder

    fun fontSize(size: Int): ConfigBuilder

    fun bold(): ConfigBuilder

    fun toUpperCase(): ConfigBuilder

    fun endConfig(): ShapeBuilder

}

interface BaseBuilder {

    fun build(text: String, color: Int): TextDrawable

}

interface ShapeBuilder {

    fun beginConfig(): ConfigBuilder

    fun rect(): BaseBuilder

    fun round(): BaseBuilder

    fun roundRect(radius: Int): BaseBuilder

    fun buildRect(text: String, color: Int): TextDrawable

    fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable

    fun buildRound(text: String, color: Int): TextDrawable

}

class TextDrawable private constructor(builder: Builder) : ShapeDrawable(builder.shape) {

    private val textPaint: Paint
    private val borderPaint: Paint
    private val text: String
    private val color: Int
    private val shape: RectShape
    private val height: Int
    private val width: Int
    private val fontSize: Int
    private val radius: Float
    private val borderThickness: Int

    init {
        // shape properties
        shape = builder.shape
        height = builder.height
        width = builder.width
        radius = builder.radius

        // text and color
        text = if (builder.toUpperCase) builder.text.toUpperCase() else builder.text
        color = builder.color

        // text paint settings
        fontSize = builder.fontSize
        textPaint = Paint()
        textPaint.color = builder.textColor
        textPaint.isAntiAlias = true
        textPaint.isFakeBoldText = builder.isBold
        textPaint.style = Paint.Style.FILL
        textPaint.typeface = builder.font
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.strokeWidth = builder.borderThickness.toFloat()

        // border paint settings
        borderThickness = builder.borderThickness
        borderPaint = Paint()
        borderPaint.color = getDarkerShade(color)
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderThickness.toFloat()

        // drawable paint color
        val paint = paint
        paint.color = color

    }

    private fun getDarkerShade(color: Int): Int {
        return Color.rgb((SHADE_FACTOR * Color.red(color)).toInt(),
                (SHADE_FACTOR * Color.green(color)).toInt(),
                (SHADE_FACTOR * Color.blue(color)).toInt())
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val r = bounds

        // draw border
        if (borderThickness > 0) {
            drawBorder(canvas)
        }

        val count = canvas.save()
        canvas.translate(r.left.toFloat(), r.top.toFloat())

        // draw text
        val width = if (this.width < 0) r.width() else this.width
        val height = if (this.height < 0) r.height() else this.height
        val fontSize = if (this.fontSize < 0) Math.min(width, height) / 2 else this.fontSize
        textPaint.textSize = fontSize.toFloat()
        canvas.drawText(text!!, (width / 2).toFloat(), height / 2 - (textPaint.descent() + textPaint.ascent()) / 2, textPaint)

        canvas.restoreToCount(count)

    }

    private fun drawBorder(canvas: Canvas) {
        val rect = RectF(bounds)
        rect.inset((borderThickness / 2).toFloat(), (borderThickness / 2).toFloat())

        when (shape) {
            is OvalShape -> canvas.drawOval(rect, borderPaint)
            is RoundRectShape -> canvas.drawRoundRect(rect, radius, radius, borderPaint)
            else -> canvas.drawRect(rect, borderPaint)
        }
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        textPaint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }

    class Builder internal constructor() : ConfigBuilder, ShapeBuilder, BaseBuilder {

        internal var text: String = ""

        internal var color: Int = Color.GRAY

        internal var borderThickness: Int = 0

        internal var width: Int = -1

        internal var height: Int = -1

        internal var font: Typeface = Typeface.create("sans-serif", Typeface.NORMAL)

        internal var shape: RectShape = RectShape()

        internal var textColor: Int = Color.WHITE

        internal var fontSize: Int = -1

        internal var isBold: Boolean = false

        internal var toUpperCase: Boolean = false

        internal var radius: Float = 0.toFloat()

        override fun width(width: Int): ConfigBuilder {
            this.width = width
            return this
        }

        override fun height(height: Int): ConfigBuilder {
            this.height = height
            return this
        }

        override fun textColor(color: Int): ConfigBuilder {
            this.textColor = color
            return this
        }

        override fun withBorder(thickness: Int): ConfigBuilder {
            this.borderThickness = thickness
            return this
        }

        override fun useFont(font: Typeface): ConfigBuilder {
            this.font = font
            return this
        }

        override fun fontSize(size: Int): ConfigBuilder {
            this.fontSize = size
            return this
        }

        override fun bold(): ConfigBuilder {
            this.isBold = true
            return this
        }

        override fun toUpperCase(): ConfigBuilder {
            this.toUpperCase = true
            return this
        }

        override fun beginConfig(): ConfigBuilder {
            return this
        }

        override fun endConfig(): ShapeBuilder {
            return this
        }

        override fun rect(): BaseBuilder {
            this.shape = RectShape()
            return this
        }

        override fun round(): BaseBuilder {
            this.shape = OvalShape()
            return this
        }

        override fun roundRect(radius: Int): BaseBuilder {
            this.radius = radius.toFloat()
            val radii = floatArrayOf(radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat())
            this.shape = RoundRectShape(radii, null, null)
            return this
        }

        override fun buildRect(text: String, color: Int): TextDrawable {
            rect()
            return build(text, color)
        }

        override fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable {
            roundRect(radius)
            return build(text, color)
        }

        override fun buildRound(text: String, color: Int): TextDrawable {
            round()
            return build(text, color)
        }

        override fun build(text: String, color: Int): TextDrawable {
            this.color = color
            this.text = text
            return TextDrawable(this)
        }
    }

    companion object {

        fun builder(): ShapeBuilder = Builder()

    }
}

private const val SHADE_FACTOR = 0.9f