package com.example.weatherapp.utils

data class CurrentWeather(
    val cod: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val feels_like: Double,
    val temp: Double
)

data class Sys(
    val country: String
)

data class Weather(
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)