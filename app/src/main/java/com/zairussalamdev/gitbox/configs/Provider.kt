package com.zairussalamdev.gitbox.configs

import android.net.Uri

class Provider {
    companion object {
        const val TABLE_NAME = "Users"
        const val AUTHORITY = "com.zairussalamdev.gitbox"
        private const val SCHEME = "content"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }
}