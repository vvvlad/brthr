package com.vvvlad42.brthr

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.os.CountDownTimer



class MainActivity : AppCompatActivity() {

//    private lateinit var boxCanvas: BoxCanvas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                TimeTxt.text = "${millisUntilFinished / 1000}"
            }
            override fun onFinish() {
                TimeTxt.text = "done!"
            }
        }.start()


//        boxCanvas.setBackgroundColor(Color.LTGRAY)
//        setContentView(boxCanvas)
//        val myDrawing = PolygonLapsDrawable()
//        val image: ImageView = imageViewPoly
//        image.setImageDrawable(myDrawing)
    }
}
