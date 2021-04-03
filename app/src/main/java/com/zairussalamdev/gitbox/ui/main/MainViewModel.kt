package com.zairussalamdev.gitbox.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.data.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: GithubUserRepository) : ViewModel() {
    private val loading = MutableLiveData(false)
    private val userList = MutableLiveData<List<User>>()
    private val errorMessage = MutableLiveData("")
    private val searchQuery = MutableLiveData("")

    fun getUsers(): LiveData<List<User>> = userList
    fun getSearchQuery() = searchQuery
    fun getLoading() = loading
    fun getErrorMessage() = errorMessage

    fun getAllUsers() {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val users = repository.getAllUser()
                userList.postValue(users)
            } catch (exception: Exception) {
                errorMessage.postValue(exception.message)
            } finally {
                setLoading(false)
            }
        }
    }

    fun searchUsers(query: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val searchResult = repository.searchUsers(query)
                userList.postValue(searchResult.users)
            } catch (exception: Exception) {
                errorMessage.postValue(exception.message)
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(status: Boolean) {
        if (status) setErrorMessage("")
        loading.postValue(status)
    }

    fun setErrorMessage(message: String) {
        errorMessage.postValue(message)
    }

}