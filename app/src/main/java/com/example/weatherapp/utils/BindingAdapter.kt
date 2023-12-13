package com.example.weatherapp.utils

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.weatherapp.R

@BindingAdapter("weatherImage")
fun ImageView.setServicesImage(item: CurrentWeather?) {
    item?.let {

        val iconId = it.weather[0].icon
        Log.i("06470647","icon -> $iconId")
        val imgUrl = "https://openweathermap.org/img/wn/$iconId@4x.png"

        Glide.with(this)
            .load(imgUrl)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.broken_image)
            .centerCrop()
            .into(this)
    }
}

@BindingAdapter("tempMain")
fun TextView.setTempMain(item: CurrentWeather?) {
    item?.let {
        val temp = it.main.temp.toInt()
        text = context.getString(R.string.temp, temp.toString())
    }
}

@BindingAdapter("feelsLike")
fun TextView.setFeelsLike(item: CurrentWeather?) {
    item?.let {
        val temp = it.main.feels_like.toInt()
        text = context.getString(R.string.feels_like, temp.toString())
    }
}

@BindingAdapter("location")
fun TextView.setLocation(item: CurrentWeather?) {
    item?.let {
        val city = it.name
        val country = it.sys.country
        text = context.getString(R.string.location, city, country)
    }
}

@BindingAdapter("condition")
fun TextView.setCondition(item: CurrentWeather?) {
    item?.let {
        val condition = it.weather[0].description
        text = context.getString(R.string.condition, condition.firstCap())
    }
}

@BindingAdapter("wind")
fun TextView.setWind(item: CurrentWeather?) {
    item?.let {
        val speed = it.wind.speed
        text = context.getString(R.string.wind_speed, speed.toString())
    }
}



