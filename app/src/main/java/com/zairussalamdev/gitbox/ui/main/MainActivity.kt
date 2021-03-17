package com.zairussalamdev.gitbox.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.gitbox.R
import com.zairussalamdev.gitbox.databinding.ActivityMainBinding
import com.zairussalamdev.gitbox.ui.DetailActivity
import com.zairussalamdev.gitbox.utils.Logger

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

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        viewModel.getAllUsers().observe(this, {
            githubUserAdapter.setUserList(it)
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
                Logger.debug(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true

        })
        return true
    }
}