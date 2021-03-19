package com.zairussalamdev.gitbox.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.gitbox.R
import com.zairussalamdev.gitbox.databinding.ActivityMainBinding
import com.zairussalamdev.gitbox.ui.adapter.GithubUserAdapter
import com.zairussalamdev.gitbox.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var githubUserAdapter: GithubUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        with(binding.rvGithubUsers) {
            layoutManager = LinearLayoutManager(binding.root.context)
            setHasFixedSize(true)
            githubUserAdapter = GithubUserAdapter {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, it.username)
                startActivity(intent)
            }
            adapter = githubUserAdapter
        }

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(MainViewModel::class.java)

        viewModel.getSearchQuery().observe(this, { query ->
            if (query.isEmpty()) {
                viewModel.getAllUsers()
            }
        })

        viewModel.getUsers().observe(this, {
            viewModel.setLoading(false)
            githubUserAdapter.setUserList(it)
            if (it.isEmpty()) {
                viewModel.setErrorMessage(resources.getString(R.string.no_data))
            } else {
                viewModel.setErrorMessage("")
            }

        })

        viewModel.getErrorMessage().observe(this, {
            val errorMessage = binding.errorMessage
            errorMessage.text = it
            errorMessage.visibility = if (it != "") View.VISIBLE else View.GONE
        })

        viewModel.getLoading().observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUsers(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_change_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
        return true
    }
}