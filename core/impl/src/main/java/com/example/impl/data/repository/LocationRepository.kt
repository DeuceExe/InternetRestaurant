package com.example.impl.data.repository

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale

class LocationRepository(private val activity: Activity){

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    val currentLocation = MutableLiveData<String>()

    private fun checkPermission(): Boolean {
        val fineLocationPermission = ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED ||
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun getLastLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location = task.result
                    if (location == null) {
                        getNewLocation()
                    } else {
                        currentLocation.value = getLocation(location.latitude, location.longitude)
                    }
                }
            } else {
                showLocationSettingsDialog()
            }
        } else {
            requestPermission()
        }
    }

    private fun getNewLocation() {
        locationRequest = LocationRequest.create().apply {
            this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            this.interval = 0
            this.fastestInterval = 0
            this.maxWaitTime = 0
            this.numUpdates = 2
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            if (lastLocation != null) {
                currentLocation.value = getLocation(lastLocation.latitude, lastLocation.longitude)
            }
        }
    }

    private fun showLocationSettingsDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Служба определения местоположения отключена")
            .setMessage("Пожалуйста, включите службы определения местоположения.")
            .setPositiveButton("Включить") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                activity.startActivity(intent)
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun getLocation(lat: Double, long: Double): String {
        var location = ""
        val geocoder = Geocoder(activity, Locale.getDefault())
        val address = geocoder.getFromLocation(lat, long, 1)

        if (address != null) {
            location = "${address[0].locality}, ${address[0].countryName}"
        }
        return location
    }

    companion object {

        const val PERMISSION_ID = 200
    }
}