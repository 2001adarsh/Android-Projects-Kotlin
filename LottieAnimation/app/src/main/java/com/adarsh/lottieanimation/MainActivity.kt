package com.adarsh.lottieanimation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var isChecked = false
    var isSwitch = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkmark.setOnClickListener{
            if(isChecked)
            {
                checkmark.speed = -1f
                checkmark.playAnimation()
                isChecked = false
            } else{
                checkmark.speed = 1f
                checkmark.playAnimation()
                isChecked = true
            }
        }


        tswitch.setOnClickListener {
            tswitch.speed = 3f
            if(isSwitch){
                tswitch.setMinAndMaxProgress(0.5f, 1.0f)
                tswitch.playAnimation()
                isSwitch =false
            } else {
                tswitch.setMinAndMaxProgress(0.0f, 0.5f)
                tswitch.playAnimation()
                isSwitch = true
            }
        }

        next.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        }

    }
}
