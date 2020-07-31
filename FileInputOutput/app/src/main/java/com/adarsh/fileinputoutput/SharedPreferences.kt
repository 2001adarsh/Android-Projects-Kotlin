package com.adarsh.fileinputoutput

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_shared_preferences.*

class SharedPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)

        val sPref = getPreferences(Context.MODE_PRIVATE)
        val color = sPref.getInt("COLOR", Color.WHITE)  //WHITE is default color. for first time, when shared Pref is not stored.
        constraintLayout.setBackgroundColor(color)

        fun saveColor(color: Int){
            val editor = sPref.edit()
            editor.putInt("COLOR", color)
            editor.apply()
        }

        btn_red.setOnClickListener {
            constraintLayout.setBackgroundColor(Color.RED)
            saveColor(Color.RED)
        }
        btn_blue.setOnClickListener {
            constraintLayout.setBackgroundColor(Color.BLUE)
            saveColor(Color.BLUE)
        }
        bnt_green.setOnClickListener {
            constraintLayout.setBackgroundColor(Color.GREEN)
            saveColor(Color.GREEN)
        }

    }
}