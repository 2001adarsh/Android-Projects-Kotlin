package com.adarsh.workmanager_jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

//Work Manager works even when the application is closed unlike coroutines.
//Better alternative to Alarms and broadcast receiver, as they have problems above api 23.

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        worker_run.setOnClickListener {
            setUpGithubWorker()
        }
    }

    private fun setUpGithubWorker() {

        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) //Wifi needed.
            .setRequiresBatteryNotLow(true) //have appropriate battery.
            .setRequiresStorageNotLow(true)
            .build()

        val workerRequest = OneTimeWorkRequestBuilder<GithubWorker>()
            .setInitialDelay(4, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()

        WorkManager.getInstance(this).enqueue(workerRequest)
    }
}