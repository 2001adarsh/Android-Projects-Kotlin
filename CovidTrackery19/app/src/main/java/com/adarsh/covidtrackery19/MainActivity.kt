package com.adarsh.covidtrackery19

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var stateAdapter: StateAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchResults()
    }

    private fun fetchResults() {
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) { Client.api.execute() }
            if(response.isSuccessful){
                //Log.i("Info: ", response.body?.string())

                val data = Gson().fromJson(response.body?.string(), com.adarsh.covidtrackery19.Response::class.java)
                launch(Dispatchers.Main){
                    bindCombinedData(data.statewise[0]);
                    bindStateWiseData(data.statewise)
                }
            }
        }
    }

    private fun bindStateWiseData(statewise: List<StatewiseItem>) {
        stateAdapter = StateAdapter(statewise)
        listView.adapter = stateAdapter

    }

    private fun bindCombinedData(get:StatewiseItem){
        val lastUpdatedTime = get.lastupdatedtime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        lastUpdatedTV.text = "Last Updated\n ${getTimeAgo(simpleDateFormat.parse(lastUpdatedTime))}"

        confirmTV.text = get.confirmed
        activeTV.text = get.active
        recoveredTV.text = get.recovered
        deceasedTV.text = get.deaths
    }


    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }


}
