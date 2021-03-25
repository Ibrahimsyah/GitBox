package com.zairussalamdev.gitbox.services

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.zairussalamdev.gitbox.configs.Provider.Companion.AUTHORITY
import com.zairussalamdev.gitbox.configs.Provider.Companion.TABLE_NAME
import com.zairussalamdev.gitbox.database.GitBoxDatabase
import com.zairussalamdev.gitbox.database.UserDao

class UserProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userDao: UserDao
    }

    init {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
    }

    override fun onCreate(): Boolean {
        userDao = GitBoxDatabase.getInstance(context as Context).userDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER -> userDao.getAllAsCursor()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}