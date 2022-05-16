package com.salihkinali.notedailyapp.view.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragmentScreen -> showBottomNav()
                R.id.addListFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }

    }

    private fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }
}