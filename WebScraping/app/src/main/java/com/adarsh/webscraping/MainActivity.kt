package com.adarsh.webscraping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            val doc = withContext(Dispatchers.IO){
                Jsoup.connect("https://codeforces.com/contests/with/2001adarshsingh").get()
            }

            val div = doc.select("div.datatable")
            val tbody = div.select("tr")

            for(i in 0 until tbody.size-1){
                val td = tbody.select("td")
                for(j in 0 until td.size-1){
                    val num = td.select("td")
                            .eq(i)
                            .text()

                    val details = td
                            .select("a")
                            .eq(i)
                            .text()
                    Log.i("Tracker _> ", "num -> $num -> $details" )
                }
            }

            Log.i("Tracker", tbody.size.toString())
        }

    }


}