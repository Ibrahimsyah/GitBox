package com.zairussalamdev.gitbox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.UserCallback
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.data.entities.UserDetail
import com.zairussalamdev.gitbox.services.GithubApiInterface
import com.zairussalamdev.gitbox.services.RetrofitService

class DetailViewModel : ViewModel() {
    private val apiService = RetrofitService.getInstance().create(GithubApiInterface::class.java)
    private val repository = GithubUserRepository.getInstance(apiService)
    private val userDetail = MutableLiveData<UserDetail>()

    fun getUserDetail(username: String): LiveData<UserDetail> {
        repository.getUserDetail(username, object : UserCallback<UserDetail> {
            override fun onSuccess(data: UserDetail) {
                userDetail.postValue(data)
            }

            override fun onError(error: String) {}
        })
        return userDetail
    }

    fun getUserFollowers(username: String): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()
        repository.getUserFollowers(username, object : UserCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
                result.postValue(data)
            }

            override fun onError(error: String) {}
        })
        return result
    }

    fun getUserFollowing(username: String): LiveData<List<User>> {
        val result = MutableLiveData<List<User>>()
        repository.getUserFollowing(username, object : UserCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
                result.postValue(data)
            }

            override fun onError(error: String) {}
        })
        return result
    }
}