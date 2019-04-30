package com.example.soulsearching2

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import java.util.*

class MainActivity : RotationBase() {

    val timer = Timer()

    lateinit var imageView : ImageView

    //Index used to cycle through instruction images
    var j : Int = 0

    //Index for instruction array
    var tIndex: Int = 0

    //Array of instructionImages
    var instructionImages = arrayOf<Int>(
        R.drawable.instructions1,
        R.drawable.instructions2,
        R.drawable.instructions3,
        R.drawable.instructions4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.supportActionBar?.hide()

        imageView = findViewById(R.id.imageView1)

        //Set movement managers
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        canTurn = true

        //Set instruction images index to RotationBase index
        tIndex = rbIndex

        //Set button imges
        val closeBtn = findViewById<ImageButton>(R.id.imageButton)
        val homeBtn = findViewById<ImageButton>(R.id.imageButton2)

        closeBtn.setOnClickListener {
            // your code to perform when the user clicks on the button
            closeBtn()
        }

        homeBtn.setOnClickListener {
            // your code to perform when the user clicks on the button
            Log.d("Button", "Clicked button")
            HomeBtn()
        }

        //Register listener to detect device movement
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
            SensorManager.SENSOR_DELAY_NORMAL
        )

        //Set the schedule function, to cycle images automatically
        timer.scheduleAtFixedRate(
            object : TimerTask() {

                override fun run() {
                    imageView.setImageResource(instructionImages[j])
                    //increase j if less that the number of instruction images, otherwise set j to 0
                    if (j < instructionImages.size - 1) {
                        j += 1
                    } else {
                        j = 0
                    }
                }
            },
            0, 3000
        )

        Log.d("Orientation","Have been created")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("Orientation","Made it on Accuracy")
    }

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
    override fun onSensorChanged(event: SensorEvent) {
        updateInstructOrientationAngles(updateOrientationAngles(event,2),updateOrientationAngles(event,1))
    }

    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.
    private fun updateInstructOrientationAngles(r: Float, p: Float) {

        when(p) {
            in -10.00..10.00 -> {
                //Log.d("Flip", "range is " + r + "index is " + index + " and canTurn is " + canTurn)
                when {
                    //Case where roll degress is between 100 and infinity, index is less then
                    //soulImages number of items and canTurn is true.
                    r in 80.00..110.00 && canTurn -> {
                        //set canTurn to false so index only decrements once
                        canTurn = false
                        val intent = Intent(this, Main2Activity::class.java)
                        intent.putExtra("index", tIndex)
                        // start your next activity
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    fun HomeBtn() {
        //unregisterListener for this class
        sensorManager.unregisterListener(this)

        //Start the next activity
        val intent = Intent(this, Main2Activity::class.java)
        intent.putExtra("index", 0)
        startActivity(intent)
        finish()

        pressedHome = true
        pressedInfo = false
    }

    fun closeBtn() {
        //unregisterListener for this class
        sensorManager.unregisterListener(this)

        //Start the next activity
        val intent = Intent(this, Main2Activity::class.java)
        intent.putExtra("index", tIndex)
        startActivity(intent)
        finish()
        pressedHome = true
        pressedInfo = false
    }
}
