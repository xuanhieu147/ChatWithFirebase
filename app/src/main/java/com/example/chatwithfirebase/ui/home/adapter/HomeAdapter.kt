package com.example.chatwithfirebase.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.chatwithfirebase.base.BaseAdapter
import com.example.chatwithfirebase.base.BaseViewHolder
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.databinding.ItemUserBinding

class HomeAdapter() :
    BaseAdapter<User, BaseViewHolder>() {

    val listUser = ArrayList<User>()

    override fun addItems(repoList: List<User>) {
        listUser.addAll(repoList)
        notifyDataSetChanged()
    }

    override fun clearItems() {
        listUser.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val dataBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        return holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return listUser?.size
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            binding.user = listUser[position]
            binding.executePendingBindings()
        }


    }

}