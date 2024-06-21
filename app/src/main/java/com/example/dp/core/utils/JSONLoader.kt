package com.example.dp.core.utils

import org.json.JSONObject
import java.io.InputStream


class JSONLoader {
    fun load(inputStream: InputStream): JSONObject {
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return JSONObject(String(buffer, Charsets.UTF_8))
    }
}