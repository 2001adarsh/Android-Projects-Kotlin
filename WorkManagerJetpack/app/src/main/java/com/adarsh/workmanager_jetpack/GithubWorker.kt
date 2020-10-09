package com.adarsh.workmanager_jetpack

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubWorker(private val context: Context, private val param: WorkerParameters) :
    CoroutineWorker(context, param) {
    override suspend fun doWork(): Result {
        val response = withContext(Dispatchers.IO) { RetroFitClient.githubApi.getUser() }
        return if (response.isSuccessful) {
            Log.e("TAG", "doWork: isSuccessful")
            Result.success()
        } else {
            Log.e("TAG", "doWork: Restarted")
            Result.retry()
        }
    }

}