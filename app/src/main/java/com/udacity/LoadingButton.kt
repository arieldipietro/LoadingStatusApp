package com.udacity

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.animation.doOnRepeat
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var currentPercentage = 0

    //Main button in unclicked state
    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 50f
        typeface = Typeface.create("Roboto", Typeface.NORMAL)
        color = context.getColor(R.color.colorPrimary)
    }

    //Button on clicked state and animation
    private val filledRectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 50f
        typeface = Typeface.create("Roboto", Typeface.NORMAL)
        color = context.getColor(R.color.colorPrimaryDark)
    }

    //text for the button
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        textAlign = Paint.Align.CENTER
        textSize = resources.getDimension(R.dimen.default_text_size)
        color = context.getColor(R.color.white)
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private val valueAnimator = ValueAnimator()

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
                Log.i("Main Activity", "State Loading")
                //starts the loading animation
                animateProgress()
                //similarly handle the other 2 states as well
                //buttonState = ButtonState.Completed
            }
            ButtonState.Completed -> {
                Log.i("Main Activity", "State Completed")
            }
        }
    }


    init {

    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw( canvas) creo que no lo necesito

        //drawing unclicked button. It's present all the time as a background color
        drawUnclickedButton(canvas)
        //draw clicked Button
        drawLoadingButton(canvas)
        //Drawing Button Text, depending on the state
        drawButtonText(canvas)

        //no se si lo voy a usar
        /*when(buttonState){
            is ButtonState.Completed -> drawButtonText()
            is ButtonState.Loading -> drawLoadingButton(canvas)
        }*/

    }

    //drawing the unclicked button
    private fun drawUnclickedButton(canvas: Canvas){
        val emptyRect = RectF(0f, heightSize.toFloat(), widthSize.toFloat(), 0f)
        canvas.drawRect(emptyRect, rectPaint)
        invalidate()
    }
    //drawing the button text, depending on the state
    private fun drawButtonText(canvas: Canvas){
        val buttonText = when(buttonState){
            is ButtonState.Completed -> resources.getString(R.string.button_download)
            is ButtonState.Loading -> resources.getString(R.string.button_loading)
        }
            resources.getString(R.string.button_download)
        val textHeight: Float = textPaint.descent() - textPaint.ascent()
        val textOffset: Float = textHeight / 2 - textPaint.descent()
        canvas.drawText(buttonText, (widthSize/2).toFloat(), heightSize.toFloat() / 2 + textOffset, textPaint)
        invalidate()
    }
    //drawing the loading button
    private fun drawLoadingButton(canvas: Canvas){
        val percentageToFill = getCurrentPercentageToFill()
        val fillingRect = RectF(0f, heightSize.toFloat(), percentageToFill, 0f)

        //animating the filled button
        canvas.drawRect(fillingRect, filledRectPaint)
        invalidate()
    }

    private fun getCurrentPercentageToFill() = (widthSize * (currentPercentage / PERCENTAGE_DIVIDER)).toFloat()

    //Followed a tutorial animation a circle progress and adapted it to fill the rectangle
    //https://medium.com/@paulnunezm/canvas-animations-simple-circle-progress-view-on-android-8309900ab8ed
    fun animateProgress() {
        //holdes animation values from 0 to 100
        val valuesHolder = PropertyValuesHolder.ofFloat("percentage", 0f, 100f)

        //instance of ValueAnimator
        val animator = ValueAnimator().apply {
            setValues(valuesHolder)
            //need to set the duration to the duration of the download
            duration = 1000
            addUpdateListener {
                val percentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                currentPercentage = percentage.toInt()
                invalidate()
            }
        }
        animator.start()
        /*animator.doOnRepeat {
            buttonState = ButtonState.Completed
        }*/
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
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

    companion object {
        const val PERCENTAGE_DIVIDER = 100.0
        const val PERCENTAGE_VALUE_HOLDER = "percentage"
    }

}