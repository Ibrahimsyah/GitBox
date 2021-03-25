package com.zairussalamdev.gitbox.data.entities

import com.google.gson.annotations.SerializedName

data class UserDetail(
        @SerializedName("login")
        val username: String = "",

        @SerializedName("avatar_url")
        val avatar: String? = "",

        @SerializedName("html_url")
        val url: String? = "",

        @SerializedName("name")
        val name: String? = "",

        @SerializedName("company")
        val company: String? = "",

        @SerializedName("location")
        val location: String? = "",

        @SerializedName("bio")
        val bio: String? = "",

        @SerializedName("public_repos")
        val repository: Int? = 0,

        @SerializedName("followers")
        val followers: Long? = 0,

        @SerializedName("following")
        val following: Long? = 0,
)