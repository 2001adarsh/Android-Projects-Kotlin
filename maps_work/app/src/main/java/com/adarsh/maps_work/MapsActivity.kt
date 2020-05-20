package com.adarsh.maps_work

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
import com.google.android.gms.maps.model.PolylineOptions
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        requestPermissionForLocation()
        when{
            checkPermission() -> {
                when{
                    isLocationEnabled() -> setUpLocationListener()
                    else -> showDialogBox()
                }
            }
            else -> requestPermissionForLocation()
        }
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
                    isLocationEnabled() -> setUpLocationListener()
                    else -> showDialogBox()
                }
            } else {
                Toast.makeText(this, "Permission of Location not given", Toast.LENGTH_SHORT).show()
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


//    @SuppressLint("MissingPermission")
//    private fun setUpLocationListener(){
//        val providers = locationManager.getProviders(true)
//        var l:Location? = null
//        for( i in providers.indices.reversed()){
//            l = locationManager.getLastKnownLocation(providers[i])
//            if(l != null)
//                break
//        }
//
//        l?.let {
//          if(::mMap.isInitialized){
//              val currentLocation = LatLng(l.latitude, l.longitude)
//              mMap.addMarker(MarkerOptions().position(currentLocation).title("Marker in Sydney"))
//              mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
//          }
//        }
//
//    }


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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.apply {
            isCompassEnabled = true
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isMyLocationButtonEnabled = true
        }
        //mMap.setMaxZoomPreference(2f)

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.addPolyline(
            PolylineOptions().add(sydney, LatLng(20.59, 78.39))
                .color(ContextCompat.getColor(baseContext, R.color.colorAccent))
                .width(2f)
        )
    }
}
