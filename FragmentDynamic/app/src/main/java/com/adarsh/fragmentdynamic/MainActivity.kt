package com.adarsh.fragmentdynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = Bundle()
        bundle.putString("KEY", "Adarsh Singh")
        val fragment = FirstFragment()
        fragment.arguments = bundle

        button.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        }
    }
}
