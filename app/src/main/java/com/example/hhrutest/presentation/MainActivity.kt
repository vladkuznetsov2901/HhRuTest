package com.example.hhrutest.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.hhrutest.R
import com.example.hhrutest.databinding.ActivityMainBinding
import com.example.hhrutest.presentation.ui.home.HomeViewModel
import com.example.hhrutest.presentation.ui.home.HomeViewModelFactory
import com.example.hhrutest.presentation.ui.sign_in.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    val viewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels { homeViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isSignIn = sharedPref.getBoolean("isSignIn", false)

        val badge = binding.navView.getOrCreateBadge(R.id.navigation_favourites)


        lifecycleScope.launch {
            if (isSignIn) {
                navController.navigate(R.id.navigation_search)
                binding.navView.setOnItemSelectedListener {
                    homeViewModel.updateBubbleCount()
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
                lifecycleScope.launch {
                    homeViewModel.vacanciesCount.collect { count ->
                        if (count > 0) {
                            badge.number = count
                            badge.isVisible = true
                        } else {
                            badge.isVisible = false
                        }
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

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.navigation_search) {
            finishAffinity()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}