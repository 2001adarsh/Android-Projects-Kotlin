package com.adarsh.maps_work

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ERROR_DIALOG_REQUEST = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(isServicesAvailable()){
            btn.setOnClickListener {
                startActivity(Intent(this, Maps2Activiy::class.java))
                finish()
            }
        }
    }


    //Checking if the user has correct version, and google play store services installed in his phone
    private fun isServicesAvailable(): Boolean{
        Log.d("IsServicesAvailable", "Checking google services version")
        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        when {
            available == ConnectionResult.SUCCESS -> {
                //everything is fine, we can make request
                Log.d("IsServicesAvailable: ", "everything is fine, we can make request")
                return true
            }
            GoogleApiAvailability.getInstance().isUserResolvableError(available) -> {
                //an error occured but we can resolve it.
                Log.d("IsServicesAvailable", "version error occurred -> Resolvable")
                val dialog = GoogleApiAvailability.getInstance().getErrorDialog(this@MainActivity , available, ERROR_DIALOG_REQUEST)
                dialog.show()
            }
            else -> {
                Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }
}
