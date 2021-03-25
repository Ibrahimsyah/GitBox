package com.zairussalamdev.gitfavorite.ui

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.gitfavorite.configs.Provider.Companion.CONTENT_URI
import com.zairussalamdev.gitfavorite.databinding.ActivityMainBinding
import com.zairussalamdev.gitfavorite.utils.CursorHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: GithubUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = GithubUserAdapter {
            Toast.makeText(this@MainActivity, it.username, Toast.LENGTH_SHORT).show()
        }

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = userAdapter
        }

        val handler = Handler(mainLooper)
        val contentObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                loadFavoriteUsers()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, contentObserver)
        loadFavoriteUsers()
    }

    private fun loadFavoriteUsers() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
                CursorHelper.mapCursorToList(cursor)
            }
            val result = deferredUsers.await()
            userAdapter.setUserList(result)
        }
    }
}