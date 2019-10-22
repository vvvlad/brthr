package com.vvvlad42.brthr

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(),BoxCanvas.ChangeListener {

    private lateinit var timer:Timer
    private var partSecondsCounter:Int = 0
    private var secondsShow:Int = 0
    private var secondsBox:Int =5
    private val refreshRateMs:Long = 100
    val instructionTexts = listOf("Breath In", "Hold", "Breath Out", "Hold")
    var actionIndex:Int=0

    override fun onChangeHappened(pathPart:Int) {
        println(pathPart)
    }

    override fun onUpdateHappened() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onPause() {
        super.onPause()
        timer.cancel()
        partSecondsCounter=0
        secondsShow=0
    }

    override fun onResume() {
        super.onResume()

        this.boxCanvas?.resetView()
        // subscribing to event
        this.boxCanvas.setChangeListener(this)

        timer= Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    runOnUiThread { updateView() }
                }
            } , 0, refreshRateMs)
    }

    private fun secondsCounter():Boolean
    {
        partSecondsCounter+= 1
        if (partSecondsCounter==10){
            secondsShow++
            partSecondsCounter=0
            if (secondsShow>secondsBox){
                secondsShow=1
            }
            return true
        }
        return false
    }
    fun updateView(){
        this.boxCanvas?.invalidate()
        val secPassed = secondsCounter()
        this.txtCount.text=secondsShow.toString()

        if (secPassed && secondsShow==secondsBox) {
            actionIndex++
            if (actionIndex > instructionTexts.size-1)
                actionIndex = 0
            vibrate(this)
        }
        this.txtAction.text = instructionTexts[actionIndex]
    }
    // Vibrates the device for 200 milliseconds.
    private fun vibrate(context: Context){
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





//        object : CountDownTimer(30000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                TimeTxt.text = "${millisUntilFinished / 1000}"
//            }
//            override fun onFinish() {
//                TimeTxt.text = "done!"
//            }
//        }.start()

//
//        boxCanvas.setBackgroundColor(Color.LTGRAY)
//        setContentView(boxCanvas)
//        val myDrawing = PolygonLapsDrawable()
//        val image: ImageView = imageViewPoly
//        image.setImageDrawable(myDrawing)
    }



}
