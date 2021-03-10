package com.zairussalamdev.gitbox.utils

import android.content.Context
import java.io.IOException

object JSONHelper {
    fun readJsonFromAsset(context: Context, filename: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(filename).bufferedReader().use {
                it.readText()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}