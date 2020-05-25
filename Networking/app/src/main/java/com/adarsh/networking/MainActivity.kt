package com.adarsh.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {

            val response = service.getPosts()

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.
                        Log.d("TAG", "success")
                    } else {
                        Toast.makeText(baseContext,"Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: HttpException) {
                    Toast.makeText(baseContext,"Exception ${e.message}", Toast.LENGTH_SHORT).show()
                } catch (e: Throwable) {
                    Toast.makeText(baseContext,"Ooops: Something else went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
