package com.example.internetrestaurant.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.api.ICategoryLauncher
import com.example.api.IFragmentReplace
import com.example.internetrestaurant.R
import com.example.internetrestaurant.databinding.ActivityMainBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.example.impl.presentation.fragments.bucketFragment.BucketFragment

class MainActivity : AppCompatActivity(), IFragmentReplace, KoinComponent {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val launcher: ICategoryLauncher by inject()
        setFragment(launcher.launch() as Fragment)

        setControlNavigation()
    }

    private fun setControlNavigation(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
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
}