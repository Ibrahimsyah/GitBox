package com.zairussalamdev.gitbox.data.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val username: String? = "",

    @SerializedName("avatar_url")
    val avatar: String? = "",

    @SerializedName("html_url")
    val url: String? = ""
)