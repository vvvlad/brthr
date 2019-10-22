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
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





class BoxCanvas(context: Context, attrs: AttributeSet) : View(context, attrs) {
    lateinit var paint: Paint
    lateinit var rect: RectF
    private var path: Path = Path()
    private lateinit var pathMeasure: PathMeasure
    private var pathLength: Float = 0.toFloat()
    private var edgeLen:Float = 0.toFloat()
    lateinit var bm: Bitmap
    private var bmOffsetX: Int = 0
    private var bmOffsetY:Int = 0

    private var step: Float = 0F
    private var distance: Float = 0F
    private var secondsPerEdge:Int = 5

    private lateinit var pos: FloatArray
    private lateinit var tan: FloatArray
    private lateinit var mtrx: Matrix

    interface ChangeListener {
        fun onChangeHappened(pathPart:Int)
        fun onUpdateHappened()
    }
    private var listener: ChangeListener? = null

    fun setChangeListener(listener: ChangeListener) {
        this.listener = listener
    }


    fun resetView(){
        initParams()
    }
    private fun initParams(){
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.rgb(72,209,204)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F

        bm = BitmapFactory.decodeResource(resources, R.drawable.ic_follow)
        bmOffsetX = bm.width /2
        bmOffsetY = bm.height /2

        step = 1F
        distance = 0F
        pos = FloatArray(2)
        tan = FloatArray(2)
        mtrx = Matrix()

    }
    private fun initMyView(){
        //Prepare the rectangle
        path.moveTo(width/2-width*0.35F, height/2-width*0.35F)
        path.lineTo(width/2+width*0.35F, height/2-width*0.35F)
        path.lineTo(width/2+width*0.35F, height/2+width*0.35F)
        path.lineTo(width/2-width*0.35F, height/2+width*0.35F)
        path.close()
        // Info about the path shown as toast
        pathMeasure = PathMeasure(path, false)
        pathLength = pathMeasure.length

        edgeLen = pathLength/4
        step = edgeLen/secondsPerEdge/10

//        Toast.makeText(context, "pathLength: $pathLength", Toast.LENGTH_LONG).show()

    }

    init {
        initParams()
        this.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initMyView()

        canvas!!.drawPath(path, paint)


        pathMeasure.getPosTan(distance, pos, tan)
        mtrx.reset()
        val degrees = (atan2(tan[1], tan[0]) * 180.0 / Math.PI).toFloat()
        mtrx.postRotate(degrees, bmOffsetX.toFloat(), bmOffsetY.toFloat())
        mtrx.postTranslate(pos[0] - bmOffsetX, pos[1] - bmOffsetY)

        canvas.drawBitmap(bm, mtrx, null)
        //val passed:Float = pathLength/edgeLen
//        if (pathLength%edgeLen==0F)
//            if (listener != null) {
//                listener?.onChangeHappened((pathLength/edgeLen).toInt())
//            }

//            for (i in 1..20)  distance += step/20
        distance += step
        if (distance>=pathLength)
            distance = step

    }
}