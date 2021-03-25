package com.zairussalamdev.gitbox.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.entities.User

class FavoriteViewModel(private val repository: GithubUserRepository) : ViewModel() {
    private val loading = MutableLiveData<Boolean>()

    fun getFavoriteUsers(): LiveData<List<User>> {
        loading.postValue(true)
        val result = repository.getFavoriteUsers()
        loading.postValue(false)
        return result
    }

    fun getLoading(): LiveData<Boolean> = loading
}