package com.vvvlad42.brthr

import android.content.Context
import android.graphics.*
import android.view.View
import android.widget.Toast
import kotlin.math.atan2

public class BoxCanvas(context: Context?) : View(context) {
    lateinit var paint: Paint
    lateinit var rect: RectF
    private var path: Path = Path()
    private lateinit var pathMeasure: PathMeasure
    var pathLength: Float = 0.toFloat()
    lateinit var bm: Bitmap
    private var bmOffsetX: Int = 0
    private var bmOffsetY:Int = 0

    private var step: Float = 0F
    private var distance: Float = 0F
    private lateinit var pos: FloatArray
    private lateinit var tan: FloatArray
    private lateinit var mtrx: Matrix


    private fun initMyView(){
        paint = Paint()
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F

        bm = BitmapFactory.decodeResource(resources, R.drawable.ic_arrow_forward_ios)
        bmOffsetX = bm.width /2
        bmOffsetY = bm.height /2

        //Prepare the rectangle (TODO See if needs to be parametrized)
        path.moveTo(width/2-width*0.35F, height/2-width*0.35F)
        path.lineTo(width/2+width*0.35F, height/2-width*0.35F)
        path.lineTo(width/2+width*0.35F, height/2+width*0.35F)
        path.lineTo(width/2-width*0.35F, height/2+width*0.35F)
        path.close()
        // Info about the path shown as toast
        pathMeasure = PathMeasure(path, false)
        pathLength = pathMeasure.length
        Toast.makeText(context, "pathLength: $pathLength", Toast.LENGTH_LONG).show()

        step = 1F
        distance = 0F
        pos = FloatArray(2)
        tan = FloatArray(2)

        mtrx = Matrix()
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
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initMyView()
        canvas?.drawPath(path, paint)

        if (distance < pathLength) {
            pathMeasure.getPosTan(distance, pos, tan)

            matrix.reset()
            val degrees = (atan2(tan[1], tan[0]) * 180.0 / Math.PI).toFloat()
            matrix.postRotate(degrees, bmOffsetX.toFloat(), bmOffsetY.toFloat())
            matrix.postTranslate(pos[0] - bmOffsetX, pos[1] - bmOffsetY)

            canvas?.drawBitmap(bm, matrix, null)

            distance += step
        } else {
            distance = 0F
        }

        paint.color = Color.RED
        paint.strokeWidth = 20F
        canvas?.drawPoint(width/2-width*0.35F, height/2-width*0.35F, paint)

    }
}