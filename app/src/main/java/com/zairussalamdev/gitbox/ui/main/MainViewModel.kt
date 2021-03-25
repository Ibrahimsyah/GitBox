package com.zairussalamdev.gitbox.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.UserCallback
import com.zairussalamdev.gitbox.data.entities.User

class MainViewModel(private val repository: GithubUserRepository) : ViewModel() {
    private val loading = MutableLiveData(false)
    private val userList = MutableLiveData<List<User>>()
    private val errorMessage = MutableLiveData("")
    private val searchQuery = MutableLiveData("")

    fun getAllUsers() {
        setLoading(true)
        repository.getAllUser(object : UserCallback<List<User>> {
            override fun onSuccess(data: List<User>) {
                userList.postValue(data)
            }

            override fun onError(error: String) {
                errorMessage.postValue(error)
            }
        })
    }

    fun searchUsers(query: String) {
        setLoading(true)
        if (query != "") {
            searchQuery.postValue(query)
            repository.searchUsers(query, object : UserCallback<List<User>> {
                override fun onSuccess(data: List<User>) {
                    userList.postValue(data)
                }

                override fun onError(error: String) {
                    errorMessage.postValue(error)
                }
            })
        } else {
            searchQuery.postValue("")
        }
    }

    fun getSearchQuery(): LiveData<String> {
        return searchQuery
    }

    fun getUsers(): LiveData<List<User>> {
        return userList
    }

    fun getErrorMessage(): LiveData<String> {
        return errorMessage
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    fun setLoading(status: Boolean) {
        if (status) setErrorMessage("")
        loading.postValue(status)
    }

    fun setErrorMessage(message: String) {
        errorMessage.postValue(message)
    }

}