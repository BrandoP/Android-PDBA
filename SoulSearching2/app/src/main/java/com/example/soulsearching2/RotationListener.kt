package com.example.soulsearching2

import android.hardware.SensorEvent

interface RotationListener {

    var canTurn: Boolean
    var pressedHome: Boolean
    var pressedInfo: Boolean

    val r : FloatArray
    val i : FloatArray
    val v : FloatArray

    var accValues : FloatArray
    var geoValues : FloatArray

    var azimuth: Float
    var pitch: Float
    var roll: Float

    fun updateOrientationAngles(e : SensorEvent, i : Int): Float
}