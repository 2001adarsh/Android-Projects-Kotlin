package com.adarsh.exintent

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert.ACTION
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val email = editText.text.toString()
            val i = Intent()
            i.action = Intent.ACTION_SENDTO
            i.data = Uri.parse("mailto:$email")
            startActivity(i)
        }
        button2.setOnClickListener {
            val phone = editText.text.toString()
            val i= Intent()
            i.action = Intent.ACTION_DIAL
            i.data = Uri.parse("tel:$phone")
            startActivity(i)
        }
        button3.setOnClickListener {
            val address = editText.text.toString()
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://$address")
            startActivity(i)
        }

    }
}
