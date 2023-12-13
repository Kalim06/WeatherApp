package com.example.weatherapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.utils.hideSoftKeyboard
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {

            val city = binding.searchInput.text.toString()

            if (city.isNotEmpty() && city.isNotBlank()) {
                getCurrentWeather(city, requireActivity().applicationContext)
            } else {
                Snackbar.make(binding.root, "Enter City", Snackbar.LENGTH_SHORT).show()
            }

            it.hideSoftKeyboard(it, requireActivity().applicationContext)
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getCurrentWeather(city: String, context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getCurrentWeather(
                    city, "metric", context.getString(R.string.api_key)
                )
            } catch (e: IOException) {
                Snackbar.make(
                    binding.root,
                    "Something went wrong. Please try again.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@launch
            } catch (e: HttpException) {
                Snackbar.make(
                    binding.root,
                    "Something went wrong. Please try again.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {

                    val data = response.body()

                    if (data != null) {
                        binding.weather = data
                    }
                }
            } else if (!response.isSuccessful) {

                withContext(Dispatchers.Main) {

                    val data = response.body()

                    if (data?.cod == 404) {
                        Snackbar.make(
                            binding.root,
                            "City not found.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Something went wrong. Please try again.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}