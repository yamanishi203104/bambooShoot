package com.example.githubtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MyView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var path : Path = Path()
    private var paint : Paint = Paint()
    private var drawX:Float = 0F
    private var drawY:Float = 0F

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20F
        canvas?.drawPath(path,paint)
    }

    fun changeColor(colorSelected: String){
        when(colorSelected){
            "白" -> paint.color = Color.WHITE
            "黄" -> paint.color = Color.YELLOW
            "黒" -> paint.color = Color.BLACK
            "赤" -> paint.color = Color.RED
        }
    }

    fun changeSize(sizeSelected: String){
        when(sizeSelected){
            "大" -> paint.strokeWidth = 20F
            "中" -> paint.strokeWidth = 10F
            "小" -> paint.strokeWidth = 5F
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        drawX = event!!.x
        drawY = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> path.moveTo(drawX,drawY)
            MotionEvent.ACTION_MOVE -> path.lineTo(drawX,drawY)
        }

        invalidate()

        //return super.onTouchEvent(event)
        return true
    }

    fun clearCanvas(){
        path.reset()
        invalidate()
    }
}