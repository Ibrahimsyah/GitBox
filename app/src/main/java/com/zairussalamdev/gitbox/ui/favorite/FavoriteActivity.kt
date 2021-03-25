package com.zairussalamdev.gitbox.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.gitbox.databinding.ActivityFavoriteBinding
import com.zairussalamdev.gitbox.ui.adapter.GithubUserAdapter
import com.zairussalamdev.gitbox.ui.detail.DetailActivity
import com.zairussalamdev.gitbox.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)

        val adapter = GithubUserAdapter {
            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, it.username)
            startActivity(intent)
        }

        viewModel.getFavoriteUsers().observe(this, {
            adapter.setUserList(it)
            binding.tvNoData.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        })

        viewModel.getLoading().observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }
}