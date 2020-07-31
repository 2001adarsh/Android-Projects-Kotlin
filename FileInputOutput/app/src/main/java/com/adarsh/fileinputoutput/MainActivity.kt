package com.adarsh.fileinputoutput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_write.setOnClickListener {
            val datadir = ContextCompat.getDataDir(this)
            val myFile = File(datadir, "file.txt")
            myFile.writeText(editTextTextPersonName.text.toString())
        }

        btn_read.setOnClickListener {
            val dataDir = ContextCompat.getDataDir(this)
            val myFile = File(dataDir, "file.txt")

            //Special Care to be taken such that this wouldn't read a file of more than 2GB. Use only when you are sure to sure that file is small.
            textView.text = myFile.readText() 
        }

    }
}