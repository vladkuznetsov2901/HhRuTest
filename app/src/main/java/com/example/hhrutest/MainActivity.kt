package com.example.hhrutest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hhrutest.databinding.ActivityMainBinding
import com.example.hhrutest.ui.sign_in.SignInViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: SignInViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isSignIn = sharedPref.getBoolean("isSignIn", false)



        lifecycleScope.launch {
            if (isSignIn) {
                binding.navView.setOnItemSelectedListener {

                    when (it.itemId) {
                        R.id.navigation_search -> {
                            findNavController(R.id.nav_host_activity_main).navigate(R.id.navigation_search)
                            true
                        }

                        R.id.navigation_favourites -> {
                            findNavController(R.id.nav_host_activity_main).navigate(R.id.navigation_favourites)
                            true
                        }

                        R.id.navigation_responses -> {
                            findNavController(R.id.nav_host_activity_main).navigate(R.id.navigation_responses)
                            true
                        }

                        else -> false
                    }
                }

            } else {
                val editor = sharedPref.edit()
                editor.putBoolean("isSignIn", true)
                editor.apply()
                navController.navigate(R.id.signInFragment)
            }
        }


    }
}