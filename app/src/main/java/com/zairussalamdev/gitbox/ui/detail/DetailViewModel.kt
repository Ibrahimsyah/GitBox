package com.zairussalamdev.gitbox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.UserCallback
import com.zairussalamdev.gitbox.data.entities.UserDetail
import com.zairussalamdev.gitbox.services.GithubApiInterface
import com.zairussalamdev.gitbox.services.RetrofitService

class DetailViewModel : ViewModel() {
    private val apiService = RetrofitService.getInstance().create(GithubApiInterface::class.java)
    private val repository = GithubUserRepository.getInstance(apiService)
    private val loading = MutableLiveData(false)
    private val userDetail = MutableLiveData<UserDetail>()
    private val errorMessage = MutableLiveData("")

    fun getUserDetail(username: String): LiveData<UserDetail> {
        loading.postValue(true)
        repository.getUserDetail(username, object : UserCallback<UserDetail> {
            override fun onSuccess(data: UserDetail) {
                userDetail.postValue(data)
            }

            override fun onError(error: String) {
                errorMessage.postValue(error)
            }
        })
        return userDetail
    }
}