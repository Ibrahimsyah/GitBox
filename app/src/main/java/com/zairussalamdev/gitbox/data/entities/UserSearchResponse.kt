package com.zairussalamdev.gitbox.data.entities

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
        @SerializedName("items")
        val users: List<User>
)