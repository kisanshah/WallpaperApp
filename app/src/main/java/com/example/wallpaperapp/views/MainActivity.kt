package com.example.wallpaperapp.views

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.wallpaperapp.R
import com.example.wallpaperapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = resources.getColor(R.color.black_primary)


        setSupportActionBar(binding.toolbar)
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        binding.toolbar.visibility = visibility
        binding.bottomNav.visibility = visibility
    }
}