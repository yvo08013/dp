package com.example.dp

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.dp.core.ui.BaseActivity
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.appComponent
import com.example.dp.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLocale(this, LANGUAGE_CODE)

        with(supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment) {

            NavigationUI.setupWithNavController(binding.navView, navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.f_home,
                    R.id.f_schedule,
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

    @Suppress("SameParameterValue")
    private fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    companion object {
        const val LANGUAGE_CODE = "ru"
    }
}