package com.example.githubtest

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import androidx.core.graphics.BitmapCompat
import androidx.core.graphics.or
import java.util.*
import kotlin.collections.ArrayDeque

/*
@see https://qiita.com/ymshun/items/a1447bdfcea8ef24d765
 */

class CustomSurfaceView: SurfaceView, SurfaceHolder.Callback{
    private var pensiz: Float = 15f
    private var surfaceHolder: SurfaceHolder? = null
    private var paint: Paint? = null
    private var path: Path? = null
    var color: Int? = null
    var prevBitmap: Bitmap? = null
    var tempBitmap: Bitmap? = null
    private var prevCanvas: Canvas? = null
    private var canvas: Canvas? = null
    val linears: Linears = Linears()

//    private val mUndoStack: ArrayDeque<Path> = ArrayDeque()
//    private val mColorStack: ArrayDeque<Int> = ArrayDeque()

//    private val linearsStack: ArrayDeque<LinearBean> = ArrayDeque()

    var width: Int? = null
    var height: Int? = null

    constructor(context: Context, surfaceView: SurfaceView) : super(context) {
        surfaceHolder = surfaceView.holder

        /// display の情報（高さ 横）を取得
        val size = Point().also {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.apply {
                getSize(
                    it
                )
            }
        }

        /// surfaceViewのサイズ
        width = size.x
        height = size.y

        /// 背景を透過させ、一番上に表示
        surfaceHolder!!.setFormat(PixelFormat.TRANSPARENT)
        surfaceView.setZOrderOnTop(true)

        /// コールバック
        surfaceHolder!!.addCallback(this)

        /// ペイント関連の設定
        paint = Paint()
        color = Color.RED
        paint!!.color = color as Int
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeCap = Paint.Cap.ROUND
        paint!!.isAntiAlias = true
        paint!!.strokeWidth = pensiz
    }

    //// pathクラスの情報とそのpathの色情報を保存する
    data class pathInfo(
        var path: Path,
        var color: Int
    )

    /// surfaceViewが作られたとき
    override fun surfaceCreated(holder: SurfaceHolder) {
        /// bitmap,canvas初期化
        initializeBitmap()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        /// bitmapをリサイクル
        prevBitmap!!.recycle()
    }


    /// bitmapとcanvasの初期化
    private fun initializeBitmap() {
        if (prevBitmap == null) {
            prevBitmap = Bitmap.createBitmap(width!!, height!!, Bitmap.Config.ARGB_8888)
        }

        if (prevCanvas == null) {
            prevCanvas = Canvas(prevBitmap!!)
        }

        prevCanvas!!.drawColor(0, PorterDuff.Mode.CLEAR)
    }

    private fun draw(pathInfo: pathInfo) {
        canvas = Canvas()

        /// ロックしてキャンバスを取得
        canvas = surfaceHolder!!.lockCanvas()

        //// キャンバスのクリア
        canvas!!.drawColor(0, PorterDuff.Mode.CLEAR)

        /// 前回のビットマップをキャンバスに描画
        canvas!!.drawBitmap(prevBitmap!!, 0F, 0F, null)

        //// pathを描画
        paint!!.color = pathInfo.color
        canvas!!.drawPath(pathInfo.path, paint!!)

        /// ロックを解除
        surfaceHolder!!.unlockCanvasAndPost(canvas)
    }

    /// 画面をタッチしたときにアクションごとに関数を呼び出す
    fun onTouch(event: MotionEvent) : Boolean{
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown(event.x, event.y)
            MotionEvent.ACTION_MOVE -> touchMove(event.x, event.y)
            MotionEvent.ACTION_UP -> touchUp(event.x, event.y)
        }
        return true
    }

    ///// path クラスで描画するポイントを保持
    ///    ACTION_DOWN 時の処理
    private fun touchDown(x: Float, y: Float) {
        path = Path()
        path!!.moveTo(x, y)
    }

    ///    ACTION_MOVE 時の処理
    private fun touchMove(x: Float, y: Float) {
        /// pathクラスとdrawメソッドで線を書く
        path!!.lineTo(x, y)
        draw(pathInfo(path!!, color!!))
    }

    ///    ACTION_UP 時の処理
    private fun touchUp(x: Float, y: Float) {
        /// pathクラスとdrawメソッドで線を書く
        path!!.lineTo(x, y)
        draw(pathInfo(path!!, color!!))
        /// 前回のキャンバスを描画
        prevCanvas!!.drawPath(path!!, paint!!)

//        mUndoStack.addLast(path!!)
//        mColorStack.addLast(color!!)

//        val paint_:Paint = Paint()
//        paint_!!.color = color!!
//        paint_!!.style = Paint.Style.STROKE
//        paint_!!.strokeCap = Paint.Cap.ROUND
//        paint_!!.isAntiAlias = true
//        paint_!!.strokeWidth = pensiz

//        linearsStack.addLast(LinearBean(path = path!!,paint = paint_!!))

//        while(linears.counter-- > 0){
//            linears.remove()
//        }
//        linears.counter = 0

        linears.add(path!!,color!!,pensiz)

//        val a = linearsStack.last()
        val a = linears.last()
        Log.d("★", "path:" + a.path!!.toString() + ", color:" + a.paint!!.color.toString())
    }

    /// resetメソッド
    fun reset() {
        ///初期化とキャンバスクリア
        initializeBitmap()
        canvas = surfaceHolder!!.lockCanvas()
        canvas?.drawColor(0, PorterDuff.Mode.CLEAR)
        surfaceHolder!!.unlockCanvasAndPost(canvas)
//        linearsStack.removeAll(linearsStack)
        linears.removeAll()
        linears.counter = 0
    }

//    val counter : Int = 0

    /// undo メソッド(仮)
    fun undo(){
//        if(mUndoStack.isEmpty()){
//            return
//        }
        if(linears.isEmpty()){
            return
        }

        if(linears.getsize() == linears.counter){
            return
        }


//        mUndoStack.removeLast()
//        mColorStack.removeLast()
        linears.redraw()

        canvas = Canvas()
        /// ロックしてキャンバスを取得
        canvas = surfaceHolder!!.lockCanvas()

        //// キャンバスのクリア
        canvas!!.drawColor(0, PorterDuff.Mode.CLEAR)

        /// ビットマップを初期化
//        tempBitmap = Bitmap.createBitmap(width!!, height!!, Bitmap.Config.ARGB_8888)
//        canvas!!.drawBitmap(tempBitmap!!, 0F, 0F, null)

        //// pathを描画
        // TODO 一つまで再現できるようにする必要あり。
//        var temp = mColorStack.size-1
//        for(path in mUndoStack){
//            paint!!.color = mColorStack[temp]
//            canvas!!.drawPath(path, paint!!)
//            temp--
//        }
        for((index, item) in linears.linears.withIndex()) {
            if(index == linears.limitLast()){
                break
            }
            canvas!!.drawPath(item.path, item.paint)
            Log.d("★★", "path:" + item.path + ", color:" + item.paint.color.toString())
        }

        /// ロックを解除
        surfaceHolder!!.unlockCanvasAndPost(canvas)

        //prevBitmap =
    }

    /// color チェンジメソッド
    fun changeColor(colorSelected: String) {
        when (colorSelected) {
            "black" -> color = Color.BLACK
            "red" -> color = Color.RED
            "white" -> color = Color.WHITE
            "yellow" -> color = Color.YELLOW
        }
        paint!!.color = color as Int
    }

    fun changePensize(pensizeSelected: String){
        when(pensizeSelected){
            "big" -> pensiz = 15F
            "mid" -> pensiz = 10F
            "small" -> pensiz = 5F
        }
        paint!!.strokeWidth = pensiz
    }
}