package com.adarsh.notificationbasics

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.drm.DrmStore
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            nm.createNotificationChannel(NotificationChannel("first", "default",
                    NotificationManager.IMPORTANCE_DEFAULT))
        }

        button.setOnClickListener {
            val simpleNotification = NotificationCompat.Builder(this, "first")
                    .setContentTitle("Simple Title!")
                    .setContentText("Hello, this is Adarsh and here is his random notification " +
                            "learning way all along.")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()
            nm.notify(1, simpleNotification)
        }

        button2.setOnClickListener {
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://www.google.com")

            val pi = PendingIntent.getActivity(this,123,i,PendingIntent.FLAG_UPDATE_CURRENT)

            val clickableButton = NotificationCompat.Builder(this, "first")
                    .setContentTitle("Simple Text 2")
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setContentText("Hello world. How are you, try clicking on the notification")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()
            nm.notify(2, clickableButton)
        }

        button3.setOnClickListener {
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse("https://www.google.com")

            val pi = PendingIntent.getActivity(this,123,i,PendingIntent.FLAG_UPDATE_CURRENT)

            val clickableButton = NotificationCompat.Builder(this, "first")
                    .setContentTitle("Simple Text 2")
                    .addAction(R.drawable.ic_launcher_foreground, "Click Here", pi)
                    .setAutoCancel(true)
                    .setContentText("Hello world. How are you, try clicking on the notification")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()
            nm.notify(3, clickableButton)
        }


    }
}
