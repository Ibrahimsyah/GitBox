package com.zairussalamdev.gitbox.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.services.GithubApiInterface
import com.zairussalamdev.gitbox.services.RetrofitService

class MainViewModel : ViewModel() {
    private val apiService = RetrofitService.getInstance().create(GithubApiInterface::class.java)
    private val repository = GithubUserRepository.getInstance(apiService)
    private val userList = MutableLiveData<List<User>>()

    fun getAllUsers() {
        val result = repository.getAllUser()
        result.observeForever {
            it?.let {
                userList.postValue(it)
            }
        }
    }

    fun getUsers(): LiveData<List<User>> {
        return userList
    }
}