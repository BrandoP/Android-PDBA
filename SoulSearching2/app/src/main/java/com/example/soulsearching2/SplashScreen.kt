package com.example.soulsearching2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        this.supportActionBar?.hide()

        Handler().postDelayed( {
            run {
                // This method will be executed once the timer is over
                val intent = Intent(this,  MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 5000)
    }
}
