package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.dictionary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        launchHomeFragment()
    }

    private fun launchHomeFragment() {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_layout, homeFragment)
            .commit()
    }
}