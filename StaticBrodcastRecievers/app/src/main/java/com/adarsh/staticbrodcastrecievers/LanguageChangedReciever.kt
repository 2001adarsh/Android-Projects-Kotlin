package com.adarsh.staticbrodcastrecievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class LanguageChangedReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("RCVD", "Language Changed, received in my app")
        Toast.makeText(context,"Language changed, received in my app", Toast.LENGTH_SHORT).show()
      // context.startActivity(Intent(context, MainActivity::class.java))
   }
}
