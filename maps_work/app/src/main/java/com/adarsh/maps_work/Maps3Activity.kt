package com.adarsh.maps_work

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.android.gms.location.FusedLocationProviderClient

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps3.*

class Maps3Activity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps3)
        setSupportActionBar(toolbar)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_for_maps, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
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

    //Setting up different Listener
    private fun setUpLocation(){
        val mFusedLocationProviderClient = FusedLocationProviderClient(this)
        val location = mFusedLocationProviderClient.lastLocation
        location.addOnCompleteListener {
            if(it.isSuccessful){
                val currentLocation = it.result
                moveCamera(LatLng(currentLocation!!.latitude, currentLocation.longitude), 15f, "My Location")
                map.isMyLocationEnabled = true
                map.uiSettings.apply {
                    isMyLocationButtonEnabled = false
                }
            }else{
                Toast.makeText(this, "UnAble to get current Location", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun moveCamera(ltnlog:LatLng, zoom:Float, title: String){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ltnlog , zoom))
        if(title != "My Location"){
            map.addMarker(MarkerOptions().title(title).position(ltnlog))
        }
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
        map = googleMap
    }

    //Requesting permissions
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
    private fun isLocationEnabled():Boolean{
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    private fun showDialogBox(){
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
