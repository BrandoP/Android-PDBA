package com.example.soulsearching2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log


abstract class RotationBase : AppCompatActivity(),RotationListener, SensorEventListener {

    override var canTurn = false
    override var pressedHome = false
    override var pressedInfo = false

    //Set the base index to 0
    var rbIndex: Int = 0

    //Variables that control senors and gyros
    lateinit var sensorManager: SensorManager
    lateinit var gyroscope: Sensor

    //Array
    override val r = FloatArray(9)
    override val i = FloatArray(9)
    override val v = FloatArray(3)

    //Variable to contain accelerometer values
    override var accValues = FloatArray(3)
    //Variable to contain magnetic values
    override var geoValues = FloatArray(3)

    //Floats for x,y,z
    override var azimuth: Float = 0.0f
    override var pitch: Float = 0.0f
    override var roll: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        rbIndex = intent.getIntExtra("index", 0)
        Log.d("Index", "The index in Rotation base is $rbIndex")
    }

    override fun updateOrientationAngles(e :SensorEvent, orientation :Int): Float {
        if (e.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            accValues = e.values.clone()
        }

        if (e.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geoValues = e.values.clone()
        }

        val success = SensorManager.getRotationMatrix(
            r, i, accValues,
            geoValues
        )

        if (success) {
            SensorManager.getOrientation(r, v)

            azimuth = (v[0] * (180 / Math.PI)).toFloat()
            pitch = (v[1] * (180 / Math.PI)).toFloat()
            roll = (v[2] * (180 / Math.PI)).toFloat()
        }

        if(orientation == 0){
            return azimuth
        }
        if(orientation == 1){
            return pitch
        }
        if(orientation == 2){
            return roll
        }else{
            return error("Number out of range, input int inbetween 0 and 2")
        }
    }
}
