package com.adarsh.lottieanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class SecondActivity : AppCompatActivity() {

    private lateinit var owl: OwlModelClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        owl = OwlModelClass(owlimg)

        CoroutineScope(Dispatchers.Main).launch {
            async {
                owlimg.progress = 17f
                owlimg.speed= 0.5f
                owlimg.setMinAndMaxFrame(5, 100)
                owlimg.playAnimation()
                Log.d("BaseActivity", " PlayAnimation started and ended.")
            }.await()
            async {
                owlimg.progress = 127f
                owlimg.speed = 0.5f
                owlimg.setMinAndMaxFrame(127, 209)
                owlimg.playAnimation()
                Log.d("BaseActivity", " ReadingAnimation started and ended.")
            }.await()

        }
    }

    suspend fun setAnimation(){
        val job = GlobalScope.launch(Dispatchers.Main){
            //val value =  owl.begin()
          //  Log.d("VALUE ->", value.toString())

            owlimg.progress = 17f
            owlimg.speed= 0.5f
            owlimg.setMinAndMaxFrame(5, 100)
            owlimg.playAnimation()
            Log.d("BaseActivity", " PlayAnimation started and ended.")
        }
        job.join()

        val job1= GlobalScope.launch(Dispatchers.Main){ owl.reading() }
         job1.join()

//        Toast.makeText(this, "Start of begin()", Toast.LENGTH_SHORT).show()
//        owl.begin()
//        Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show()
//        owl.pause()
//        Toast.makeText(this, "Start of reading()", Toast.LENGTH_SHORT).show()
//        Thread.sleep(000)
//        owl.reading()

    }
}
