package com.udacity

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class OkButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var currentPercentage = 0

    //ok button in unclicked state
    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 50f
        typeface = Typeface.create("Roboto", Typeface.NORMAL)
        color = context.getColor(R.color.orange_button)
    }

    //text for the button
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = resources.getDimension(R.dimen.default_text_size)
        color = context.getColor(R.color.white)
        typeface = Typeface.create("", Typeface.BOLD)
    }

    //drawing button
    private fun drawButton(canvas: Canvas) {
        val emptyRect = RectF(0f, heightSize.toFloat(), widthSize.toFloat(), 0f)
        canvas.drawRect(emptyRect, rectPaint)
        invalidate()
    }

    //drawing the button text
    private fun drawButtonText(canvas: Canvas) {
        val buttonText = resources.getString(R.string.button_ok)
        val textHeight: Float = textPaint.descent() - textPaint.ascent()
        val textOffset: Float = textHeight / 2 - textPaint.descent()
        canvas.drawText(
            buttonText,
            (widthSize / 2).toFloat(),
            heightSize.toFloat() / 2 + textOffset,
            textPaint
        )
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawButton(canvas)
        drawButtonText(canvas)
    }
}