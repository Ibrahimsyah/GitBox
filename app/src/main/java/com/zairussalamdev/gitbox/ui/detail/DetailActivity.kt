package com.zairussalamdev.gitbox.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.zairussalamdev.gitbox.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER)
        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(DetailViewModel::class.java)

        username?.let {
            viewModel.getUserDetail(username).observe(this, {
//                with(binding) {
//                    userName.text = it.name
//                    userUsername.text = it.username
//                    userRepository.text = it.repository.toString()
//                    userFollower.text = it.followers.toString()
//                    userFollowing.text = it.following.toString()
//                    userCompany.text = it.company
//                    userLocation.text = it.location
//
//                    userImage.load(it.avatar) {
//                        crossfade(true)
//                        transformations(CircleCropTransformation())
//                    }
//                }
            })
        }

        val tabTitles = arrayOf("Followers", "Following")
        with(binding) {
            viewpager.adapter = ViewPagerAdapter(this@DetailActivity)
            TabLayoutMediator(tabs, viewpager) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()
        }
    }
}