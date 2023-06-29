package com.example.weatherapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.domain.WeatherEntity
import com.example.weatherapp.presentation.viewmodels.WeatherViewModel
import com.example.weatherapp.domain.NetworkResult
import com.google.android.material.snackbar.Snackbar

class WeatherDetailsFragment : Fragment() {

    private var _binding: FragmentWeatherDetailsBinding? = null
    private val binding: FragmentWeatherDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentWeatherDetailsBinding is null")

    private val weatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherViewModel.loadCurrentWeather()
        weatherViewModel.currentWeather.observe(viewLifecycleOwner) {
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
                    Snackbar.make(binding.root, "Loading...", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setCurrentWeather(currentWeather: WeatherEntity) {
        val tempTemplate = this.resources.getString(R.string.degrees)
        val visibilityTemplate = this.resources.getString(R.string.kilometres)
        val cloudsTemplate = this.resources.getString(R.string.percent)

        with(binding) {
            with(currentWeather){
                tvCity.text = city
                tvFeelsLike.text = String.format(tempTemplate, feelsLike)
                tvTempMin.text = String.format(tempTemplate, tempMin)
                tvTempMax.text = String.format(tempTemplate, tempMax)
                tvVisibility.text = String.format(visibilityTemplate, visibility)
                tvClouds.text = String.format(cloudsTemplate, clouds)
                tvWindDirection.text = windDirection.toString()
                tvSunrise.text = sunrise
                tvSunset.text = sunset
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment = WeatherDetailsFragment()
    }
}