package com.example.weatherapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityCurrentWeatherBinding
import com.example.weatherapp.domain.WeatherEntity
import com.example.weatherapp.presentation.viewmodels.WeatherViewModel
import com.example.weatherapp.domain.NetworkResult
import com.example.weatherapp.presentation.fragments.ForecastFragment
import com.example.weatherapp.presentation.fragments.WeatherDetailsFragment
import com.google.android.material.snackbar.Snackbar

class CurrentWeatherActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel

    private val binding by lazy {
        ActivityCurrentWeatherBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnWeatherDetails.setOnClickListener {
            if (isOnePaneMode()) {
                launchWeatherDetailsActivity()
            } else {
                launchDetailsFragment()
            }
        }

        binding.btnForecast.setOnClickListener {
            if (isOnePaneMode()) {
                launchForecastActivity()
            } else {
                launchForecastFragment()
            }
        }

        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        weatherViewModel.loadCurrentWeather()
        setObservers()
    }

    private fun setObservers() {
        weatherViewModel.currentWeather.observe( this) {
            when (it) {
                is NetworkResult.Success -> {
                    val currentWeather = it.data
                    setCurrentWeather(currentWeather!!)
                }
                is NetworkResult.Error -> {
                    if (it.data != null) {
                        val currentWeather = it.data
                        setCurrentWeather(currentWeather)
                    }
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setCurrentWeather(currentWeather: WeatherEntity) {
        val tempTemplate = this.resources.getString(R.string.degrees)
        val pressureTemplate = this.resources.getString(R.string.mmHG)
        val humidityTemplate = this.resources.getString(R.string.percent)
        val windTemplate = this.resources.getString(R.string.m_in_sec)

        with(binding) {
            with(currentWeather) {
                tvTemperature.text = String.format(tempTemplate, temperature)
                tvPressure.text = String.format(pressureTemplate, pressure)
                tvCity.text = city
                tvHumidity.text = String.format(humidityTemplate, humidity)
                tvWind.text = String.format(windTemplate, windSpeed)
            }
        }
    }

    private fun isOnePaneMode() = binding.fragmentContainerCurrent == null

    private fun launchWeatherDetailsActivity() {
        startActivity(WeatherDetailsActivity.newIntent(this))
    }

    private fun launchForecastActivity() {
        startActivity(ForecastActivity.newInstance(this))
    }

    private fun launchDetailsFragment() {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_current, WeatherDetailsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun launchForecastFragment() {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_current, ForecastFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}