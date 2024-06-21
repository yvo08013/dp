package com.example.dp.core.utils

import android.content.Context
import java.io.InputStream
import javax.inject.Inject


class AssetProvider @Inject constructor(private val context: Context) {

    fun getAsset(fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}