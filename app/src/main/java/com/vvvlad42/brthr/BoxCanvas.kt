package com.vvvlad42.brthr

import android.content.Context
import android.graphics.*
import android.graphics.Path.Direction.CW
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.math.atan2

class BoxCanvas(context: Context?, attrs: AttributeSet?=null) : View(context) {
    lateinit var paint: Paint
    lateinit var rect: RectF
    private var path: Path = Path()
    private lateinit var pathMeasure: PathMeasure
    private var pathLength: Float = 0.toFloat()
    lateinit var bm: Bitmap
    private var bmOffsetX: Int = 0
    private var bmOffsetY:Int = 0

    private var step: Float = 0F
    private var distance: Float = 0F
    private lateinit var pos: FloatArray
    private lateinit var tan: FloatArray
    private lateinit var mtrx: Matrix

    private fun initParams(){
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.rgb(72,209,204)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F

        bm = BitmapFactory.decodeResource(resources, R.drawable.ic_arrow_forward_ios)
        bmOffsetX = bm.width /2
        bmOffsetY = bm.height /2

        step = 1F
        distance = 0F
        pos = FloatArray(2)
        tan = FloatArray(2)
        mtrx = Matrix()

    }
    private fun initMyView(){


        //Prepare the rectangle (TODO See if needs to be parametrized)
        path.moveTo(width/2-width*0.35F, height/2-width*0.35F)
        path.lineTo(width/2+width*0.35F, height/2-width*0.35F)
        path.lineTo(width/2+width*0.35F, height/2+width*0.35F)
        path.lineTo(width/2-width*0.35F, height/2+width*0.35F)
        path.close()
        // Info about the path shown as toast
        pathMeasure = PathMeasure(path, false)
        pathLength = pathMeasure.length
//        Toast.makeText(context, "pathLength: $pathLength", Toast.LENGTH_LONG).show()


        // Alternative rectangle implementation, but don't have path info?
        //        this.rect = RectF(width/2-width*0.35F,
        //            height/2-width*0.35F,
        //            width/2+width*0.35F,
        //            height/2+width*0.35F)
        //
        //        canvas?.drawRoundRect(
        //            this.rect,
        //            20F, 20F,
        //            paint
        //        )
    }

    init {
        initParams()
        this.setBackgroundColor(Color.rgb(255,255,240))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initMyView()
//        val d: Drawable = ContextCompat.getDrawable(context, R.drawable.autumn)!!
//        d.setBounds(left, top, right, bottom)
//        d.draw(canvas!!)

        canvas!!.drawPath(path, paint)

        if (distance < pathLength) {
            pathMeasure.getPosTan(distance, pos, tan)

            mtrx.reset()
            val degrees = (atan2(tan[1], tan[0]) * 180.0 / Math.PI).toFloat()
            mtrx.postRotate(degrees, bmOffsetX.toFloat(), bmOffsetY.toFloat())
            mtrx.postTranslate(pos[0] - bmOffsetX, pos[1] - bmOffsetY)

            canvas.drawBitmap(bm, mtrx, null)
//            paint.color = Color.RED
//            paint.strokeWidth = 20F
//            canvas.drawPoint(mtrx[0]f, mtrx[1], paint)

            for (i in 1..20)  distance += step

        } else {
            distance = 0F
        }
        invalidate()
//        paint.color = Color.RED
//        paint.strokeWidth = 20F
//        canvas?.drawPoint(width/2-width*0.35F, height/2-width*0.35F, paint)
//        invalidate()
    }
}