package com.zairussalamdev.gitbox.ui.main

import android.content.Context
import com.zairussalamdev.gitbox.data.GithubUser

class MainViewModel(private val context: Context) {

    fun getAllUsers(): List<GithubUser> {
        return GithubUser.populateUsersData(context)
    }
}