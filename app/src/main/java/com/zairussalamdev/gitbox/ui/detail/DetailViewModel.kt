package com.zairussalamdev.gitbox.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.UserCallback
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.data.entities.UserDetail

class DetailViewModel(private val repository: GithubUserRepository) : ViewModel() {
    private val loading = MutableLiveData<Boolean>()
    private val userDetail = MutableLiveData<UserDetail>()
    private val isFavorite = MutableLiveData<Boolean>()
    private val followers = MutableLiveData<List<User>>()
    private val following = MutableLiveData<List<User>>()

    fun getUserDetail(username: String): LiveData<UserDetail> {
        loading.postValue(true)
        repository.getUserDetail(username, object : UserCallback<UserDetail> {
            override fun onSuccess(data: UserDetail) {
                userDetail.postValue(data)
                loading.postValue(false)
            }

            override fun onError(error: String) {}
        })
        return userDetail
    }

    fun getUserFollowers(username: String): LiveData<List<User>> {
        loading.postValue(true)
        if (followers.value == null) {
            repository.getUserFollowers(username, object : UserCallback<List<User>> {
                override fun onSuccess(data: List<User>) {
                    followers.postValue(data)
                    loading.postValue(false)
                }

                override fun onError(error: String) {}
            })
        }
        return followers
    }

    fun getUserFollowing(username: String): LiveData<List<User>> {
        loading.postValue(true)
        if (following.value == null) {
            repository.getUserFollowing(username, object : UserCallback<List<User>> {
                override fun onSuccess(data: List<User>) {
                    following.postValue(data)
                    loading.postValue(false)
                }

                override fun onError(error: String) {}
            })
        }
        return following
    }

    fun getLoading(): LiveData<Boolean> = loading

    fun getUserIsFavorite(name: String): LiveData<Boolean> {
        val result = repository.checkUserFavorite(name)
        isFavorite.postValue(result.value)
        return result
    }

    fun addUserToFavorite(user: User) {
        repository.addFavoriteUser(user)
        getUserIsFavorite(user.username)
    }

    fun removeUserFromFavorite(user: User) {
        repository.deleteFavoriteUser(user)
        getUserIsFavorite(user.username)
    }
}