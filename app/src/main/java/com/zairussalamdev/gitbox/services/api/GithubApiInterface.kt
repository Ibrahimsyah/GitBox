package com.zairussalamdev.gitbox.services.api

import com.zairussalamdev.gitbox.BuildConfig
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.data.entities.UserDetail
import com.zairussalamdev.gitbox.data.entities.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = BuildConfig.GITHUB_API_KEY

interface GithubApiInterface {
    @Headers("Authorization: token $apiKey")
    @GET("users")
    suspend fun getAllUsers(): List<User>

    @Headers("Authorization: token $apiKey")
    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String): UserSearchResponse

    @Headers("Authorization: token $apiKey")
    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserDetail

    @Headers("Authorization: token $apiKey")
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): List<User>

    @Headers("Authorization: token $apiKey")
    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): List<User>
}