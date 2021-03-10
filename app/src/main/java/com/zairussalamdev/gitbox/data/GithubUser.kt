package com.zairussalamdev.gitbox.data

import android.content.Context
import android.os.Parcelable
import com.google.gson.Gson
import com.zairussalamdev.gitbox.utils.JSONHelper
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
data class GithubUser(
    val username: String,
    val name: String,
    val avatar: String,
    val company: String,
    val location: String,
    val repository: Int,
    val follower: Int,
    val following: Int,
) : Parcelable {
    companion object {
        fun populateUsersData(context: Context): List<GithubUser> {
            val results: MutableList<GithubUser> = mutableListOf()
            val jsonString = JSONHelper.readJsonFromAsset(context, "githubusers.json")
            jsonString?.let {
                val result = JSONObject(jsonString).getString("users")
                val gson = Gson()
                val res = gson.fromJson(result, Array<GithubUser>::class.java)
                results.addAll(res)
            }
            return results
        }
    }
}
