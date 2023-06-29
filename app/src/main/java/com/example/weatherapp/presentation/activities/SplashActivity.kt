package com.example.weatherapp.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(SPLASH_TIME)
            val intent = Intent(this@SplashActivity, CurrentWeatherActivity::class.java)
            startActivity(intent)
        }
        Log.d(this.javaClass.simpleName, "splash")
    }

    companion object {
        private const val SPLASH_TIME = 1000L
    }
}