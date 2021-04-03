package com.zairussalamdev.gitbox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.data.entities.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: GithubUserRepository) : ViewModel() {
    private val loading = MutableLiveData<Boolean>()
    private val userDetail = MutableLiveData<UserDetail>()
    private val isFavorite = MutableLiveData<Boolean>()
    private val followers = MutableLiveData<List<User>>()
    private val following = MutableLiveData<List<User>>()

    fun getLoading() = loading

    fun getUserDetail(username: String): LiveData<UserDetail> {
        loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getUserDetail(username)
            userDetail.postValue(result)
            loading.postValue(false)
        }
        return userDetail
    }

    fun getUserFollowers(username: String): LiveData<List<User>> {
        loading.postValue(true)
        if (followers.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getUserFollowers(username)
                followers.postValue(result)
                loading.postValue(false)
            }
        }
        return followers
    }

    fun getUserFollowing(username: String): LiveData<List<User>> {
        loading.postValue(true)
        if (following.value == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getUserFollowing(username)
                following.postValue(result)
                loading.postValue(false)
            }
        }
        return following
    }

    fun getUserIsFavorite(name: String): LiveData<Boolean> {
        val result = repository.checkUserFavorite(name)
        isFavorite.postValue(result.value)
        return result
    }

    fun addUserToFavorite(user: User) {
        viewModelScope.launch {
            repository.addFavoriteUser(user)
            getUserIsFavorite(user.username)
        }
    }

    fun removeUserFromFavorite(user: User) {
        viewModelScope.launch {
            repository.deleteFavoriteUser(user)
            getUserIsFavorite(user.username)
        }
    }
}