package com.zairussalamdev.gitbox.data.entities

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("login")
    val username: String? = "",

    @SerializedName("avatar_url")
    val avatar: String? = "",

    @SerializedName("name")
    val name: String? = "",

    @SerializedName("company")
    val company: String? = "",

    @SerializedName("location")
    val location: String? = "",

    @SerializedName("followers")
    val followers: String? = "",

    @SerializedName("following")
    val following: String? = "",
)