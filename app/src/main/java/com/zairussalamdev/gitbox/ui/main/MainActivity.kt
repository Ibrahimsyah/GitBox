package com.zairussalamdev.gitbox.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.gitbox.databinding.ActivityMainBinding
import com.zairussalamdev.gitbox.ui.DetailActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel = MainViewModel(this)
        val result = viewModel.getAllUsers()

        with(binding.rvGithubUsers) {
            layoutManager = LinearLayoutManager(binding.root.context)
            setHasFixedSize(true)
            adapter = GithubUserAdapter(result) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USER, it)
                startActivity(intent)
            }
        }
    }
}