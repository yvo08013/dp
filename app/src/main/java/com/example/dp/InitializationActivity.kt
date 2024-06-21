package com.example.dp

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.dp.core.ui.BaseActivity
import com.example.dp.core.utils.appComponent
import com.example.dp.databinding.ActivityInitializationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InitializationActivity : BaseActivity<ActivityInitializationBinding>(
    ActivityInitializationBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                prepopulateDB()
            }
            startActivity(Intent(this@InitializationActivity, MainActivity::class.java))
            finish()
        }
    }

    private suspend fun prepopulateDB() {
        delay(1000) //fake loading timer

        appComponent.dataBase.apply {
            clearAllTables() //nuke table after each launch to avoid conflicts while testing
        }
    }
}