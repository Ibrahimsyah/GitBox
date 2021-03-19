package com.zairussalamdev.gitbox.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zairussalamdev.gitbox.data.entities.User
import com.zairussalamdev.gitbox.databinding.FragmentFollowerBinding
import com.zairussalamdev.gitbox.ui.adapter.GithubUserAdapter

class FollowerFragment : Fragment() {

    companion object {
        const val ARG_USERNAME = "ARG_USERNAME"
        const val ARG_ISFOLLOWERS = "ARG_ISFOLLOWERS"
    }

    private lateinit var binding: FragmentFollowerBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME) ?: ""
        val isFollower = arguments?.getBoolean(ARG_ISFOLLOWERS) ?: true

        val userAdapter = GithubUserAdapter {}
        with(binding.rvFollowers) {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = userAdapter
        }

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(DetailViewModel::class.java)

        val users: LiveData<List<User>> =
                if (isFollower) viewModel.getUserFollowers(username)
                else viewModel.getUserFollowing(username)

        users.observe(viewLifecycleOwner, {
            userAdapter.setUserList(it)
        })
    }
}