package com.zairussalamdev.gitfavorite.configs

import android.net.Uri

class Provider {
    companion object {
        private const val TABLE_NAME = "Users"
        private const val AUTHORITY = "com.zairussalamdev.gitbox"
        private const val SCHEME = "content"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }
}