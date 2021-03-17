package com.zairussalamdev.gitbox.services

import com.zairussalamdev.gitbox.BuildConfig
import com.zairussalamdev.gitbox.data.entities.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

class RetrofitService {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private var retrofit: Retrofit? = null
        fun getInstance(): Retrofit {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit as Retrofit
        }
    }
}

const val apiKey = BuildConfig.GITHUB_API_KEY

interface GithubApiInterface {
    @Headers("Authorization: token $apiKey")
    @GET("/users")
    fun getAllUsers(): Call<List<User>>

    @Headers("Authorization: token $apiKey")
    @GET("/search?q={query}")
    fun searchUser(@Path("query") query: String): Call<List<User>>
}