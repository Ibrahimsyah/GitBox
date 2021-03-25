package com.zairussalamdev.gitbox.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.zairussalamdev.gitbox.R
import com.zairussalamdev.gitbox.data.entities.UserDetail
import com.zairussalamdev.gitbox.databinding.ActivityDetailBinding
import com.zairussalamdev.gitbox.ui.adapter.ViewPagerAdapter
import com.zairussalamdev.gitbox.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
        val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.followings
        )
    }

    private lateinit var user: UserDetail
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER) ?: ""

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        viewModel.getUserDetail(username).observe(this, {
            user = it
            with(binding) {
                userName.text = it.name
                userUsername.text = it.username
                userRepository.text = it.repository.toString()
                userFollower.text = it.followers.toString()
                userFollowing.text = it.following.toString()
                userCompany.text = it.company
                userLocation.text = it.location

                userImage.load(it.avatar) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }
        })

        viewModel.getLoading().observe(this, {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        with(binding) {
            viewpager.adapter = ViewPagerAdapter(this@DetailActivity, username)
            TabLayoutMediator(tabs, viewpager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            fabFavorite.setOnClickListener { handleFavoriteClick() }
        }
    }

    private fun handleFavoriteClick() {
        Toast.makeText(this, "${user.name} Clicked!", Toast.LENGTH_SHORT).show()
    }
}