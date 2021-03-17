package com.zairussalamdev.gitbox.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.services.GithubApiInterface
import com.zairussalamdev.gitbox.services.RetrofitService

class MainViewModel : ViewModel() {
    private val apiService = RetrofitService.getInstance().create(GithubApiInterface::class.java)
    private val repository = GithubUserRepository.getInstance(apiService)
    fun getAllUsers(): LiveData<List<User>> {
        return repository.getAllUser()
    }
}