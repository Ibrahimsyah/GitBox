package com.zairussalamdev.gitbox.data

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import com.zairussalamdev.gitbox.configs.Provider.Companion.CONTENT_URI
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.database.UserDao
import com.zairussalamdev.gitbox.services.api.GithubApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubUserRepository(
    private val apiService: GithubApiInterface,
    private val userDao: UserDao,
    private val contentResolver: ContentResolver
) {
    companion object {
        private var instance: GithubUserRepository? = null
        fun getInstance(
            apiService: GithubApiInterface,
            userDao: UserDao,
            contentResolver: ContentResolver
        ): GithubUserRepository {
            if (instance == null) {
                instance = GithubUserRepository(apiService, userDao, contentResolver)
            }
            return instance as GithubUserRepository
        }
    }

    suspend fun getAllUser() = apiService.getAllUsers()

    suspend fun searchUsers(query: String) = apiService.searchUser(query)

    suspend fun getUserDetail(username: String) = apiService.getUserDetail(username)

    suspend fun getUserFollowers(username: String) = apiService.getUserFollowers(username)

    suspend fun getUserFollowing(username: String) = apiService.getUserFollowing(username)

    fun getFavoriteUsers(): LiveData<List<User>> = userDao.getAll()

    suspend fun addFavoriteUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
        contentResolver.notifyChange(CONTENT_URI, null)
    }

    suspend fun deleteFavoriteUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.delete(user)
        }
        contentResolver.notifyChange(CONTENT_URI, null)
    }

    fun checkUserFavorite(name: String): LiveData<Boolean> = userDao.checkAvailability(name)
}