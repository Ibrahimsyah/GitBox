package com.zairussalamdev.gitbox.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.CircleCropTransformation
import com.zairussalamdev.gitbox.data.GithubUser
import com.zairussalamdev.gitbox.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<GithubUser>(EXTRA_USER)
        user?.let {
            with(binding) {
                userName.text = user.name
                userUsername.text = user.username
                userRepository.text = user.repository.toString()
                userFollower.text = user.follower.toString()
                userFollowing.text = user.following.toString()
                userCompany.text = user.company
                userLocation.text = user.location
                val image =
                    resources.getIdentifier(user.avatar, "drawable", root.context.packageName)
                userImage.load(image) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }
}