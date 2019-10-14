package com.vvvlad42.brthr

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var boxCanvas: BoxCanvas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        boxCanvas = BoxCanvas(this)
        boxCanvas.setBackgroundColor(Color.LTGRAY)
        setContentView(boxCanvas)
//        val myDrawing = PolygonLapsDrawable()
//        val image: ImageView = imageViewPoly
//        image.setImageDrawable(myDrawing)
    }
}
