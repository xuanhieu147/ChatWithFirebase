package com.example.chatwithfirebase.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithfirebase.base.BaseAdapter
import com.example.chatwithfirebase.base.BaseViewHolder
import com.example.chatwithfirebase.base.OnItemClickListener
import com.example.chatwithfirebase.base.OnItemClickListener2
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.databinding.ItemUserBinding
import com.example.chatwithfirebase.databinding.ItemUserChattedBinding
import com.example.chatwithfirebase.utils.DateUtils

class UserChattedAdapter : BaseAdapter<User, BaseViewHolder>() {

    private val listUser = ArrayList<User>()
    private var onItemClickListener2: OnItemClickListener2? = null

    override fun addItems(repoList: List<User>) {
        listUser.addAll(repoList)
        notifyDataSetChanged()
    }

    override fun clearItems() {
        listUser.clear()
    }

    fun setOnItemClickListener2(listener: OnItemClickListener2) {
        onItemClickListener2 = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val dataBinding =
            ItemUserChattedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return listUser?.size
    }

    inner class UserViewHolder(private val binding: ItemUserChattedBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            binding.user = listUser[position]
            binding.executePendingBindings()
            itemView.setOnClickListener {
                onItemClickListener2?.onItemClick2(position)
            }

            // set show date and time
            if (binding.tvDate.text.toString() == DateUtils.getCurrentDate()) {
                binding.tvTime.visibility = View.VISIBLE
                binding.tvDate.visibility = View.GONE
            } else {
                binding.tvTime.visibility = View.GONE
                binding.tvDate.apply {
                    visibility = View.VISIBLE
                    text = text.toString() + "  " + binding.tvTime.text.toString()
                }
            }
        }

    }
}