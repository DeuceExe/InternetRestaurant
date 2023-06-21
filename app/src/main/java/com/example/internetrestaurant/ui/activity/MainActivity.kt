package com.example.internetrestaurant.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.api.ICategoryLauncher
import com.example.api.IFragmentReplace
import com.example.impl.model.Menu
import com.example.impl.presentation.dialog.CustomDialog
import com.example.impl.presentation.fragments.bucketFragment.BucketFragment
import com.example.impl.utils.putParcelable
import com.example.internetrestaurant.R
import com.example.internetrestaurant.databinding.ActivityMainBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), IFragmentReplace, KoinComponent {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startDefaultFragment()
        setControlNavigation()
    }

    private fun startDefaultFragment(){
        val launcher: ICategoryLauncher by inject()
        setFragment(launcher.launch() as Fragment)
    }

    private fun setControlNavigation(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.main -> startDefaultFragment()
                R.id.bucket -> replaceFragment(BucketFragment())
            }
            true
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
    }

    override fun onStop() {
        super.onStop()
        val orderDetails: SharedPreferences = getSharedPreferences("orderDetails", MODE_PRIVATE)
        val edit: SharedPreferences.Editor = orderDetails.edit()
        edit.putParcelable(CustomDialog.SHARED_BUNDLE, Menu(arrayListOf()))
        edit.apply()
    }
}