package com.zairussalamdev.gitbox.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zairussalamdev.gitbox.ui.detail.FollowerFragment

class ViewPagerAdapter(
        activity: AppCompatActivity,
        private val username: String
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFragment()
        val isFollowerFragment = when (position) {
            0 -> true
            1 -> false
            else -> true

        }
        fragment.arguments = Bundle().apply {
            putString(FollowerFragment.ARG_USERNAME, username)
            putBoolean(FollowerFragment.ARG_IS_FOLLOWERS, isFollowerFragment)
        }
        return fragment
    }

}