package com.zairussalamdev.gitbox.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Users")
data class User(
        @SerializedName("login")
        @PrimaryKey
        val username: String = "",

        @SerializedName("avatar_url")
        @ColumnInfo(name = "avatar_url")
        val avatar: String? = "",

        @SerializedName("html_url")
        @ColumnInfo(name = "html_url")
        val url: String? = ""
)