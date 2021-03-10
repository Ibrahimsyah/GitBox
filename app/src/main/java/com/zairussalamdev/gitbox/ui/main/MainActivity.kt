package com.zairussalamdev.gitbox.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.gitbox.databinding.ActivityMainBinding

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
                Toast.makeText(binding.root.context, it.name, Toast.LENGTH_SHORT).show()
            }
        }
    }
}