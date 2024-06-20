package com.example.dp.core.utils

import android.content.Context
import com.example.dp.BaseApplication
import com.example.dp.BaseApplicationComponent


val Context.appComponent: BaseApplicationComponent
    get() = when (this) {
        is BaseApplication -> applicationComponent
        else               -> this.applicationContext.appComponent
    }
