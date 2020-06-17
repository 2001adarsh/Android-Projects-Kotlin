package com.adarsh.weatherapi.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.adarsh.weatherapi.R
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class WeatherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)


        /*//Retrofit work
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.api.getLatLon("26.27,82.07")
            }
            if(response.isSuccessful){
                Log.i("Networking", response.body().toString())
                response.body()?.let {
                    Picasso.get().load(it.current?.weatherIcons?.get(0)).into(imageView2)
                    location_name.text = it.location?.name
                }
            }
        }*/

        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url("http://api.weatherstack.com/current?access_key=35451e4a1d425e4905283829f50283a5&query=26.15,82.17")
            .build()
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        GlobalScope.launch (Dispatchers.Main){
            val response = withContext(Dispatchers.IO){
                okHttpClient.newCall(request).execute().body?.string()
            }
             Log.i("Networking", "${response}")

            val weather = gson.fromJson<WeatherResponse>(response, WeatherResponse::class.java)
            Picasso.get().load(weather.current?.weatherIcons?.get(0)).into(imageView2)
            location_name.text = weather.location?.name

        }


    }



}