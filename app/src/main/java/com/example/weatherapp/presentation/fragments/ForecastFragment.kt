package com.example.weatherapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.presentation.adapters.ForecastAdapter
import com.example.weatherapp.presentation.viewmodels.WeatherViewModel
import com.example.weatherapp.domain.NetworkResult
import com.google.android.material.snackbar.Snackbar

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding: FragmentForecastBinding
        get() = _binding ?: throw RuntimeException("FragmentForecastBinding is null")

    private val weatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ForecastAdapter(requireActivity())
        binding.rvForecast.adapter = adapter
        weatherViewModel.loadForecast()
        weatherViewModel.forecast.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    adapter.list = it.data!!
                }
                is NetworkResult.Error -> {
                    if (it.data != null) {
                        adapter.list = it.data
                    }
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    Snackbar.make(binding.root, "Loading...", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
       fun newInstance(): Fragment = ForecastFragment()
    }
}