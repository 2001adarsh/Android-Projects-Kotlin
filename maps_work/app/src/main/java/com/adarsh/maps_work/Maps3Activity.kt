package com.adarsh.maps_work

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
import kotlinx.android.synthetic.main.activity_maps3.*
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class Maps3Activity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var map: GoogleMap
    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val TAG = Maps3Activity::class.java.simpleName
    private var polyline: Polyline? = null

    private lateinit var clusterManager: ClusterManager<ClusterMarkers>
    private lateinit var myClusterManagerRender : MyClusterManagerRender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps3)
        setSupportActionBar(toolbar)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setMapStyle(map)
        setMapLongClick(map)
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

    // Styling the Map
    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.dark_map_style)
                )
            if(!success) {
                Log.e(TAG, "Styling parsing failed.")
            }
        }catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }

    private fun addMapMarkers(){
        clusterManager = ClusterManager<ClusterMarkers>(this,map)
        myClusterManagerRender = MyClusterManagerRender(this, map, clusterManager)
        clusterManager.renderer = myClusterManagerRender

        val title = "Rohtak"
        val snippet = "Driver is coming"
        val avatar:Int = R.drawable.marker
        val ltnlog:LatLng = LatLng(28.840656, 76.603336)

        val clusterMarkers = ClusterMarkers(ltnlog, title, snippet, avatar)
        clusterManager.addItem(clusterMarkers)
        clusterManager.cluster()
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
                    isMyLocationButtonEnabled = true
                    isZoomGesturesEnabled = true
                    isZoomControlsEnabled = true
                    isCompassEnabled = true
                    isRotateGesturesEnabled = true
                }
                getRoad(currentLocation);
            }else{
                Toast.makeText(this, "Unable to get current Location", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getRoad(currentLocation: Location){
        val url = getUrl(currentLocation)
        addMapMarkers()
       // FetchURL(this@Maps3Activity).execute(url, "driving")
    }

    private fun getUrl(currentLocation: Location):String{
        val origin = "${currentLocation.latitude},${currentLocation.longitude}"
        val finalPlace = MarkerOptions().position(LatLng(30.772926, 76.576455)).title("Chandigarh University")
           // .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)) .flat(true).anchor(0.5F, 0.5F)
           // .rotation(90.0F) .zIndex(0.5F)

        val destination = "30.772926,76.576455"
        val mode = "driving"
        var url = "https://maps.googleapis.com/maps/api/directions/json?origin=${currentLocation.latitude},${currentLocation.longitude}&destination=${destination}&mode=$mode" +
                "&key=${getString(R.string.google_maps_key)}"
        map.addMarker(finalPlace)

        Log.d(TAG, url)
        return url
    }

    inner class getDirection(val url:String): AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg p0: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body?.string()
            val result = ArrayList<List<LatLng>>()
            try {

            }catch (e:Exception){

            }
            return result
        }

    }

    private fun moveCamera(ltnlog:LatLng, zoom:Float, title: String){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ltnlog , zoom))
        if(title != "My Location"){
            map.addMarker(MarkerOptions().title(title).position(ltnlog) )
        }
    }

    private fun setMapLongClick(map:GoogleMap) {
        map.setOnMapLongClickListener {
            map.addMarker(
                MarkerOptions()
                    .position(it)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .draggable(true)
                //icon for changing the color of the marker.
            )
        }
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
