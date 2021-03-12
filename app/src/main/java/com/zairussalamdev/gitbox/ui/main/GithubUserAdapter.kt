package com.zairussalamdev.gitbox.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zairussalamdev.gitbox.data.GithubUser
import com.zairussalamdev.gitbox.databinding.ItemRecyclerviewUsersBinding

class GithubUserAdapter(
    private val githubUser: List<GithubUser>,
    private val listener: (user: GithubUser) -> Unit
) : RecyclerView.Adapter<GithubUserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemRecyclerviewUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUser) {
            with(binding) {
                val image = root.resources.getIdentifier(
                        user.avatar,
                        "drawable",
                        root.context.packageName
                )
                userImage.load(image) {
                    crossfade(true)
                }
                userName.text = user.name
                userUsername.text = user.username
                userCompany.text = user.company
                itemView.setOnClickListener { listener(user) }
            }
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