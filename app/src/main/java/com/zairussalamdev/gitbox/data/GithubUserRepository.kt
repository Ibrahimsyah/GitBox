package com.zairussalamdev.gitbox.data

import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.data.entities.UserDetail
import com.zairussalamdev.gitbox.data.entities.UserSearchResponse
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

    fun getAllUser(callback: UserCallback<List<User>>) {
        apiService.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val users = response.body()
                users?.let { callback.onSuccess(it) }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                val message = t.message
                message?.let { callback.onError(it) }
            }
        })
    }

    fun searchUsers(query: String, callback: UserCallback<List<User>>) {
        apiService.searchUser(query).enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                    call: Call<UserSearchResponse>,
                    response: Response<UserSearchResponse>
            ) {
                val users = response.body()
                users?.let { callback.onSuccess(it.users) }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                t.message?.let { callback.onError(it) }
            }
        })
    }

    fun getUserDetail(username: String, callback: UserCallback<UserDetail>) {
        apiService.getUserDetail(username).enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                val detail = response.body()
                detail?.let { callback.onSuccess(detail) }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                t.message?.let { callback.onError(it) }
            }

        })
    }

    fun getUserFollowers(username: String, callback: UserCallback<List<User>>) {
        apiService.getUserFollowers(username).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val users = response.body()
                users?.let { callback.onSuccess(it) }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.message?.let { callback.onError(it) }
            }

        })
    }

    fun getUserFollowing(username: String, callback: UserCallback<List<User>>) {
        apiService.getUserFollowing(username).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val users = response.body()
                users?.let { callback.onSuccess(it) }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.message?.let { callback.onError(it) }
            }

        })
    }
}

interface UserCallback<T> {
    fun onSuccess(data: T)
    fun onError(error: String)
}