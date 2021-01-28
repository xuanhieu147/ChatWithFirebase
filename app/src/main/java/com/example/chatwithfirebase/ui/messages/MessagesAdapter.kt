package com.example.chatwithfirebase.ui.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.chatwithfirebase.base.BaseAdapter
import com.example.chatwithfirebase.base.BaseViewHolder
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.databinding.ItemListUsersBinding

class MessagesAdapter(
    val listUser: ArrayList<User>
) :
    BaseAdapter<User, BaseViewHolder>() {

    override fun addItems(repoList: List<User>) {
        listUser.addAll(repoList)
        notifyDataSetChanged()
    }

    override fun clearItems() {
        listUser.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val dataBinding =
            ItemListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        return holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return listUser?.size
    }

    inner class ViewHolder(private val binding: ItemListUsersBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            binding.itemMessage = listUser[position]
            binding.executePendingBindings()
        }


    }

}