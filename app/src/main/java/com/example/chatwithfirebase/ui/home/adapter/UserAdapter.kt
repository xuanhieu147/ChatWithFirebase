package com.example.chatwithfirebase.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.chatwithfirebase.base.BaseAdapter
import com.example.chatwithfirebase.base.BaseViewHolder
import com.example.chatwithfirebase.base.OnItemClickListener
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.databinding.ItemUserBinding

class UserAdapter : BaseAdapter<User, BaseViewHolder>() {

    private val listUser = ArrayList<User>()
    private var onItemClickListener: OnItemClickListener? = null

    override fun addItems(repoList: List<User>) {
        listUser.addAll(repoList)
        notifyDataSetChanged()
    }

    override fun clearItems() {
        listUser.clear()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val dataBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return listUser?.size
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            binding.user = listUser[position]
            binding.executePendingBindings()
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position)
            }
        }
    }
}