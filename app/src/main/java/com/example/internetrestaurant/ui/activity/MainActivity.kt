package com.example.internetrestaurant.ui.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.api.ICategoryLauncher
import com.example.api.IFragmentReplace
import com.example.internetrestaurant.R
import com.example.internetrestaurant.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.appcompat.app.AlertDialog
import android.provider.Settings
import android.content.Intent
import android.view.View
import com.example.api.IToolbarChange

class MainActivity : AppCompatActivity(), IFragmentReplace, IToolbarChange, KoinComponent {

    private lateinit var binding: ActivityMainBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val launcher: ICategoryLauncher by inject()
        setFragment(launcher.launch() as Fragment)

        val currentDate = getCurrentDate()
        binding.tvDate.text = currentDate

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun checkPermission(): Boolean {
        val fineLocationPermission = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED ||
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location = task.result
                    if (location == null) {
                        getNewLocation()
                    } else {
                        binding.tvLocation.text = getLocation(location.latitude, location.longitude)
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
                binding.tvLocation.text = getLocation(lastLocation.latitude, lastLocation.longitude)
            }
        }
    }

    private fun showLocationSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Location Services Required")
            .setMessage("Please enable location services to use this feature.")
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun getLocation(lat: Double, long: Double): String {
        var location = ""
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(lat, long, 1)

        if (address != null) {
            location = "${address[0].locality}, ${address[0].countryName}"
        }
        return location
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "You have permission")
            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        showCategory()

        binding.toolbar.setNavigationOnClickListener {
            hideCategory()
        }
    }

    private fun showCategory() {
        binding.tvLocation.visibility = View.GONE
        binding.tvDate.visibility = View.GONE
        binding.imageLocation.visibility = View.GONE
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val backIcon = resources.getDrawable(R.drawable.ic_back, theme)
        binding.toolbar.navigationIcon = backIcon
    }

    private fun hideCategory() {
        onBackPressedDispatcher.onBackPressed()
        binding.tvLocation.visibility = View.VISIBLE
        binding.tvDate.visibility = View.VISIBLE
        binding.imageLocation.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.tvCategory.text = ""
    }

    override fun getCategory(categoryName: String) {
        binding.tvCategory.text = categoryName
    }

    override fun onResume() {
        super.onResume()
        getLastLocation()
    }

    companion object {

        const val PERMISSION_ID = 200
    }
}