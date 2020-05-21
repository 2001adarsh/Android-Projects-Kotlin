package com.adarsh.maps_work

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_maps2_activiy.*

class Maps2Activiy : AppCompatActivity() , OnMapReadyCallback {

    private lateinit var mMap:GoogleMap
    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps2_activiy)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)

        search_edit_frame.setOnEditorActionListener(TextView.OnEditorActionListener { textview, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    keyEvent.action == KeyEvent.ACTION_DOWN ||
                    keyEvent.action == KeyEvent.KEYCODE_ENTER) {
                // execute our method for searching
                geoLocate()
            }
            false
        })

        gps_go.setOnClickListener {
            setUpLocation()
        }
    }

    private fun geoLocate() {
        Log.d("GeoLocate", "geoLocating")
        val searchString = search_edit_frame.text.toString()
        val geocoder = Geocoder(this@Maps2Activiy)
        val list = geocoder.getFromLocationName(searchString, 1)
        if(list.size > 0){
            val address = list[0]
            Log.d("GeoLocate: ", "location: "+ address.toString())
            moveCamera(LatLng(address.latitude, address.longitude), 15f, address.getAddressLine(0))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        requestPermissionForLocation()
        when{
            checkPermission() -> {
                when{
                    isLocationEnabled() -> setUpLocation()  //setUpLocationListener()
                    else -> showDialogBox()
                }
            }
            else -> requestPermissionForLocation()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
    }

    private fun moveCamera(ltnlog:LatLng, zoom:Float, title: String){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltnlog , zoom))
        if(title != "My Location"){
            mMap.addMarker(MarkerOptions().title(title).position(ltnlog))
        }
    }

    //Setting up different Listener
    private fun setUpLocation(){
        val mFusedLocationProviderClient = FusedLocationProviderClient(this)
        val location = mFusedLocationProviderClient.lastLocation
        location.addOnCompleteListener {
            if(it.isSuccessful){
                val currentLocation = it.result
                moveCamera(LatLng(currentLocation!!.latitude, currentLocation.longitude), 15f, "My Location")
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.apply {
                    isMyLocationButtonEnabled = false
                }
            }else{
                Toast.makeText(this, "UnAble to get current Location", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Fused Location Listner
    private fun setUpLocationListener(){
        val fusedLocationProviderClient = FusedLocationProviderClient(this)
        val locationRequest =  LocationRequest()
            .setInterval(2000) //milli sec
            .setFastestInterval(2000)
            .setSmallestDisplacement(1f)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations){
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    if(::mMap.isInitialized){
                        mMap.addMarker(MarkerOptions().position(currentLocation).title("Selected Marker"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                    }
                }
            }
        }
            , Looper.myLooper()
        )

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermission(): Boolean {
        return checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermissionForLocation() {
        this.requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 123)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            123 -> if(checkPermission()) {
                when{
                    isLocationEnabled() -> setUpLocation() //setUpLocationListener()
                    else -> showDialogBox()
                }
            } else {
                Toast.makeText(this, "Permission of Location not given", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //Fused Location Manager
    private fun isLocationEnabled():Boolean{
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    fun showDialogBox(){
        AlertDialog.Builder(this)
            .setTitle("Enable GPS")
            .setCancelable(false)
            .setMessage("GPS is required for Google Map")
            .setPositiveButton( "Enable Now") { dialogInterface: DialogInterface, _: Int ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                dialogInterface.dismiss()
            }.show()
    }

}
