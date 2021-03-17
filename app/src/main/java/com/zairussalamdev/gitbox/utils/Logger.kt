package com.zairussalamdev.gitbox.utils

import android.util.Log

object Logger {
    const val TAG = "gitbox-logger"

    fun debug(msg: String) {
        Log.d(TAG, msg)
    }
}