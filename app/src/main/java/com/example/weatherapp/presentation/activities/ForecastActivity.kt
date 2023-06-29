package com.example.weatherapp.presentation.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityForecastBinding
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.presentation.fragments.ForecastFragment

class ForecastActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityForecastBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_forecast, ForecastFragment.newInstance())
                .commit()
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(
            context,
            ForecastActivity::class.java
        )
    }
}