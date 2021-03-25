package com.zairussalamdev.gitbox.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zairussalamdev.gitbox.data.GithubUserRepository
import com.zairussalamdev.gitbox.database.GitBoxDatabase
import com.zairussalamdev.gitbox.services.GithubApiInterface
import com.zairussalamdev.gitbox.services.RetrofitService
import com.zairussalamdev.gitbox.ui.detail.DetailViewModel
import com.zairussalamdev.gitbox.ui.favorite.FavoriteViewModel
import com.zairussalamdev.gitbox.ui.main.MainViewModel

class ViewModelFactory(private val repository: GithubUserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            val userDao = GitBoxDatabase.getInstance(context).userDao()
            val apiService = RetrofitService.getInstance().create(GithubApiInterface::class.java)
            val contentResolver = context.contentResolver
            val repository = GithubUserRepository.getInstance(apiService, userDao, contentResolver)

            if (instance == null) {
                synchronized(this) {
                    instance = ViewModelFactory(repository)
                }
            }
            return instance as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(repository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->
                FavoriteViewModel(repository) as T
            else -> MainViewModel(repository) as T
        }
    }
}