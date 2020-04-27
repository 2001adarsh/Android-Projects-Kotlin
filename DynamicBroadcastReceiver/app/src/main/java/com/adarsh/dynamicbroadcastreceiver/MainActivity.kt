package com.adarsh.dynamicbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val psr = Myreceiver()
        val iFilter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(psr,iFilter)
    }

    inner class Myreceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            if(intent?.action == Intent.ACTION_POWER_CONNECTED){
                Toast.makeText(this@MainActivity, "Power is connected!", Toast.LENGTH_SHORT).show()
            }
            else if(intent?.action == Intent.ACTION_POWER_DISCONNECTED){
                Toast.makeText(this@MainActivity, "Power is disconnected!", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
