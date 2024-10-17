package com.example.str10

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.round

class AnimationView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val handler = Handler()
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 3f
        style = Paint.Style.FILL
    }
    private val ball = BitmapFactory.decodeResource(resources, R.drawable.ball)
    private var level = 1000
    private var x = 0
    private var y = level
    private var i = 0

    companion object {
        const val FRAME_RATE = 30
    }


    private var xCoords: List<Double> = emptyList()
    private var yCoords: List<Double> = emptyList()

    fun setCoords(xCoords: List<Double>, yCoords: List<Double>) {
        this.xCoords = xCoords
        this.yCoords = yCoords
        this.i = 0
    }

    private val runnable = object : Runnable {
        override fun run() {
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (i < xCoords.size) {
            x = round((xCoords[i])).toInt()
            y = level - round((yCoords[i] )).toInt()
            i++
        }
            canvas.drawBitmap(ball, x.toFloat(), y.toFloat(), null)

            paint.color = Color.GREEN
            paint.strokeWidth = 10f

            val groundY = level.toFloat() + 85
            canvas.drawLine(0f, groundY, width.toFloat(), groundY, paint)

            paint.color = Color.BLACK
            paint.strokeWidth = 3f

            handler.postDelayed(runnable, FRAME_RATE.toLong())
    }
}
