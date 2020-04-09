package com.adarsh.trashproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var addBut:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /* Method 1
         addBut = findViewById<Button>(R.id.button)

        //Elvis operator
        addBut?.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@MainActivity, "hello", Toast.LENGTH_SHORT).show()
        })

         */


    //import kotlinx.android.synthetic.main.activity_main.*  which automatically calls button values from xml file
        button.setOnClickListener(View.OnClickListener {
            val result = editText.text.toString().toInt() + editText2.text.toString().toInt()
            textView.text = result.toString()
        })

        /* Since SetOnclickListener has only 1 function inside it.
        //Method 3
        button.setOnClickListener({
            val result = editText.text.toString().toInt() + editText2.text.toString().toInt()
            textView.text = result.toString()
        })
         */

    }
}
