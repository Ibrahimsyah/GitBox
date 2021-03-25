package com.zairussalamdev.gitbox.services

import com.zairussalamdev.gitbox.BuildConfig
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.data.entities.UserDetail
import com.zairussalamdev.gitbox.data.entities.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = BuildConfig.GITHUB_API_KEY

interface GithubApiInterface {
    @Headers("Authorization: token $apiKey")
    @GET("users")
    fun getAllUsers(): Call<List<User>>

    @Headers("Authorization: token $apiKey")
    @GET("search/users")
    fun searchUser(@Query("q") query: String): Call<UserSearchResponse>

    @Headers("Authorization: token $apiKey")
    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetail>

    @Headers("Authorization: token $apiKey")
    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<User>>

    @Headers("Authorization: token $apiKey")
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<User>>
}