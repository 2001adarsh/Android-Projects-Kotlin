package com.adarsh.trashproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var addBut = findViewById<Button>(R.id.button)

        addBut.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@MainActivity, "hello", Toast.LENGTH_SHORT).show()
        })

    }
}
