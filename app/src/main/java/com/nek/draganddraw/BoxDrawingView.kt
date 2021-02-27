package com.nek.draganddraw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "BoxDrawingView"
private const val BOXEN_KEY = "BOXEN_KEY"

class BoxDrawingView(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private var currentBox: Box? = null
    private var boxen = mutableListOf<Box>()
    private val
            boxPaint = Paint().apply {
        color =
            0x22ff0000.toInt()
    }
    private val
            backgroundPaint = Paint().apply {
        color =
            0xfff8efe0.toInt()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        boxen.forEach { box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val b = Bundle()
        b.putParcelable(BOXEN_KEY, super.onSaveInstanceState())
        b.putParcelableArrayList(BOXEN_KEY, ArrayList<Box>(boxen))
        Log.d(TAG, "saved: $b")
        return b
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState((state as Bundle).getParcelable(BOXEN_KEY))
        //TODO: I'm not sure about this
        boxen = (state as Bundle).getParcelableArrayList(BOXEN_KEY) ?: mutableListOf()
        Log.d(TAG, "Bundle: $boxen")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                currentBox = Box(current).also {
                    boxen.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }
        Log.i(TAG, "$action at x=${current.x}, y=${current.y}")
        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }

    }
}
