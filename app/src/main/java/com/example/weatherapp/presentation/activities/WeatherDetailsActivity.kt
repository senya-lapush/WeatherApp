package com.example.weatherapp.presentation.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityWeatherDetailsBinding
import com.example.weatherapp.presentation.fragments.WeatherDetailsFragment

class WeatherDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWeatherDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_details, WeatherDetailsFragment.newInstance())
                .commit()
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(
            context,
            WeatherDetailsActivity::class.java
        )
    }
}