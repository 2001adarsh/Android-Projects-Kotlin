package com.adarsh.weatherapi.weather

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://api.weatherstack.com/current?access_key=35451e4a1d425e4905283829f50283a5")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val api = retrofit.create(WeatherService::class.java)
}