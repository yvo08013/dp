package com.example.dp.core.utils

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import javax.inject.Inject


class PrefUtils @Inject constructor(context: Context) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var userID: Int = DEFAULT_USER_ID_VALUE
        get() = preferences.getInt(USER_ID, DEFAULT_USER_ID_VALUE)
        set(value) {
            preferences.edit { putInt(USER_ID, value) }
            field = value
        }


    companion object {
        private const val USER_ID = "USER_ID"
        const val DEFAULT_USER_ID_VALUE = -1
    }
}