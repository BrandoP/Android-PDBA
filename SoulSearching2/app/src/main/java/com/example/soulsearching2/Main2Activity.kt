package com.example.soulsearching2

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView

class Main2Activity : RotationBase() {

    lateinit var imageView : ImageView

    //The index for this activity
    var index: Int = 0

    //Array of images the traverse
    var soulImages = arrayOf(
        R.drawable.soulsearching_0000_layer1,
        R.drawable.soulsearching_0001_layer2,
        R.drawable.soulsearching_0002_layer3,
        R.drawable.soulsearching_0003_layer4,
        R.drawable.soulsearching_0004_layer5,
        R.drawable.soulsearching_0005_layer6,
        R.drawable.soulsearching_0006_layer7,
        R.drawable.soulsearching_0007_layer8,
        R.drawable.soulsearching_0008_layer9,
        R.drawable.soulsearching_0009_layer10,
        R.drawable.soulsearching_0010_layer11,
        R.drawable.soulsearching_0011_layer12,
        R.drawable.soulsearching_0012_layer13,
        R.drawable.soulsearching_0013_layer14,
        R.drawable.soulsearching_0014_layer15,
        R.drawable.soulsearching_0015_layer16,
        R.drawable.soulsearching_0017_layer18,
        R.drawable.soulsearching_0018_layer19
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        this.supportActionBar?.hide()

        Log.d("Index", "Index is Main scene is $index")

        imageView = findViewById(R.id.imageView2)

        val infoBtn = findViewById<ImageButton>(R.id.imageButton)
        val homeBtn = findViewById<ImageButton>(R.id.imageButton2)

        Log.d("Index", "rbIndex in create is $rbIndex")
        //Set index to RotationBase() index
        index = rbIndex
        imageView.setImageResource(this.soulImages[index])
        Log.d("Index", "index in create is $index")

        //Set buttons method
        infoBtn.setOnClickListener {
            // your code to perform when the user clicks on the button
            Log.d("Button", "Clicked button")
            InfoBtn()
        }

        homeBtn.setOnClickListener {
            // your code to perform when the user clicks on the button
            Log.d("Button", "Clicked button")
            HomeBtn()
        }

        //Register listener to detect phone movement
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
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("Orientation","Made it on Accuracy changed")
    }


    override fun onSensorChanged(event: SensorEvent) {
        updateMainOrientationAngles(updateOrientationAngles(event,2),updateOrientationAngles(event,1))
    }

    private fun updateMainOrientationAngles(r: Float, p: Float) {
       // Log.d("Flip","Rotation is " + r + "In before method and can turn is " + this.canTurn + " and index is " + index + " and image view is " + this.soulImages[index].toString())

        //If pitch(y) of device is between 10 and -10, the allow horizontal detection
        when(p) {
            in -10.00..10.00 -> {
                Log.d("Flip", "range is " + r + " index is " + index + " and canTurn is " + canTurn + " numb of images " + soulImages.size)
                when {
                    //Case where roll decrease is between 30 and 90, index is less then
                    //soulImages number of items and canTurn is true.
                    r > 30.00 && r < 90.00  && canTurn -> {
                        //set canTurn to false so index only decrements once
                        Log.d("In", "In up turn")
                        canTurn = false

                        //If index is less than soulImages array - 1, increase index and change image
                        if(index < soulImages.size - 1) {
                            index += 1
                            imageView.setImageResource(this.soulImages[index])
                        }

                        Log.d(
                            "Turn",
                            "In rotation up,Rotation r: $r can turn is: $canTurn ,index: $index"
                        )
                    }

                    r < 150.00 && r > 90.00  && canTurn -> {
                        //set canTurn to false so index only decrements once
                        Log.d("In", "In down turn")
                        canTurn = false

                        //If index is greater than 0, decrease index and change image
                        if(index > 0) {
                            index -= 1
                            imageView.setImageResource(this.soulImages[index])
                        }

                        Log.d(
                            "Turn",
                            "In rotation Down,Rotation r: $r  can turn is: $canTurn ,Can turn down/up:,index: $index"
                        )
                    }

                    r in -120.00..-50.00 -> {
                        canTurn = true

                        Log.d(
                            "Turn",
                            "In rotation F1,Rotation r: $r  can turn is: $canTurn ,Can turn down/up: ,index: $index"
                        )
                    }
                }
            }
        }
    }

    //Sets image to first image in soulImages
   private fun HomeBtn() {
        //print("Home button has been pressed")
        imageView.setImageResource(this.soulImages[0])
        index = 0
        pressedHome = true
        canTurn = true
        pressedInfo = false
    }

    //Changes to the instruction views
    private fun InfoBtn() {
        sensorManager.unregisterListener(this);
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
        finish()
        pressedInfo = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("index", index)
    }

    //Click on button to open link
    /*
    fun didTapLink(sender: Any) {
        if (#available(iOS 10.0, *)) {
            UIApplication.shared.open(URL(string = "http://pdba.usask.ca/")!!, options = mapOf(), completionHandler = null)
        } else {
            UIApplication.shared.openURL(URL(string = "http://pdba.usask.ca/")!!)
        }
    }
    */
}
