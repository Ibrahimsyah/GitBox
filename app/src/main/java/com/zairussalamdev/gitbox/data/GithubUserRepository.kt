package com.zairussalamdev.gitbox.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.services.GithubApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubUserRepository(private val apiService: GithubApiInterface) {
    companion object {
        private var instance: GithubUserRepository? = null
        fun getInstance(apiService: GithubApiInterface): GithubUserRepository {
            if (instance == null) {
                instance = GithubUserRepository(apiService)
            }
            return instance as GithubUserRepository
        }
    }

    fun getAllUser(): LiveData<List<User>> {
        val result: MutableLiveData<List<User>> = MutableLiveData()
        apiService.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val users = response.body()
                result.postValue(users)
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
        return result
    }
}