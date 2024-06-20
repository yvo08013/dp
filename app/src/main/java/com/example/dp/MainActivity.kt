package com.example.dp

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.dp.core.ui.BaseActivity
import com.example.dp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment) {
            NavigationUI.setupWithNavController(binding.navView, navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.f_home,
                    R.id.f_dashboard,
                    R.id.f_notifications -> binding.navView.isVisible = true
                    else                 -> binding.navView.isVisible = false
                }
            }
        }
    }
}