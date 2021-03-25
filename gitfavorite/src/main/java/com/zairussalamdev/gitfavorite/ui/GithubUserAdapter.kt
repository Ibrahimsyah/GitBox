package com.zairussalamdev.gitfavorite.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zairussalamdev.gitfavorite.data.User
import com.zairussalamdev.gitfavorite.databinding.ItemRecyclerviewUsersBinding

class GithubUserAdapter(
    private val listener: (user: User) -> Unit
) : RecyclerView.Adapter<GithubUserAdapter.UserViewHolder>() {
    private var githubUser: List<User> = listOf()

    fun setUserList(users: List<User>) {
        githubUser = users
        this.notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemRecyclerviewUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                userImage.load(user.avatar) {
                    crossfade(true)
                }
                userName.text = user.username
                userUrl.text = user.url
            }
            itemView.setOnClickListener { listener(user) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            ItemRecyclerviewUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(githubUser[position])
    }

    override fun getItemCount(): Int = githubUser.size
}