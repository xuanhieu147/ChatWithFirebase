package com.example.chatwithfirebase.ui.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatwithfirebase.base.BaseAdapter
import com.example.chatwithfirebase.base.BaseViewHolder
import com.example.chatwithfirebase.data.model.RecyclerViewClickListener
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.databinding.ItemListUsersBinding

class MessagesAdapter(
    mContext: Context,
    listUser: List<User>,
    private val listener: RecyclerViewClickListener
) :
    BaseAdapter<User, BaseViewHolder>() {

    private val listUser: List<User>
    private val context: Context

    init {
        this.listUser = listUser
        this.context = mContext
    }

    override fun addItems(repoList: List<User>) {

        notifyDataSetChanged()
    }

    override fun clearItems() {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ItemListUsersBinding.inflate(inflater, parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        return holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ViewHolder(private val binding: ItemListUsersBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(position: Int) {
            val itemModel: User = listUser[position]
            binding.itemMessage = itemModel
            Glide.with(context).load(itemModel.linkImage).into(binding.imgAvatar)
            binding.constraintLayout.setOnClickListener {
                listener.onRecyclerViewItemClick(binding.constraintLayout)
            }
            binding.executePendingBindings()
        }


    }

}