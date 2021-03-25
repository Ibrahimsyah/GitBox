package com.zairussalamdev.gitfavorite.utils

import android.database.Cursor
import com.zairussalamdev.gitfavorite.data.User

object CursorHelper {
    fun mapCursorToList(cursor: Cursor?): List<User> {
        val userList = ArrayList<User>()
        val username = "username"
        val avatar = "avatar_url"
        val url = "html_url"
        cursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(username))
                val avatar = getString(getColumnIndexOrThrow(avatar))
                val url = getString(getColumnIndexOrThrow(url))
                val user = User(username, avatar, url)
                userList.add(user)
            }
        }
        return userList
    }
}