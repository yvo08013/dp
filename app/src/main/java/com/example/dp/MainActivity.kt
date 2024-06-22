package com.example.dp

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.dp.core.ui.BaseActivity
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.appComponent
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
                    R.id.f_profile -> binding.navView.isVisible = true

                    else                 -> binding.navView.isVisible = false
                }
            }

            appComponent.prefUtils.userID = PrefUtils.DEFAULT_USER_ID_VALUE //TODO remove after tests
            if (appComponent.prefUtils.userID == PrefUtils.DEFAULT_USER_ID_VALUE) {
                val inflater = navController.navInflater
                val graph = inflater.inflate(R.navigation.mobile_navigation)
                graph.setStartDestination(R.id.f_auth)
                navController.graph = graph
            }
        }
    }
}