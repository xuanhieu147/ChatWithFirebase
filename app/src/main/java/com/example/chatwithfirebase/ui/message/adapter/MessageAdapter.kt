package com.example.chatwithfirebase.ui.message.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseAdapter
import com.example.chatwithfirebase.base.BaseViewHolder
import com.example.chatwithfirebase.data.model.Message
import com.example.chatwithfirebase.data.repository.data.FirebaseDataRepository
import com.example.chatwithfirebase.databinding.MessageItemLeftBinding
import com.example.chatwithfirebase.databinding.MessageItemRightBinding
import com.example.chatwithfirebase.utils.DateUtils
import java.util.ArrayList

class MessageAdapter(private val firebaseDataRepository: FirebaseDataRepository) :
    BaseAdapter<Message, BaseViewHolder>() {

    companion object {
        private const val MESSAGE_TYPE_LEFT = 0
        private const val MESSAGE_TYPE_RIGHT = 1
    }

    private val messageList = ArrayList<Message>()

    override fun addItems(repoList: List<Message>) {
        messageList.addAll(repoList)
        notifyDataSetChanged()
    }

    override fun clearItems() {
        messageList.clear()
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].senderId == firebaseDataRepository.getCurrentUserId()) {
            MESSAGE_TYPE_RIGHT
        } else {
            MESSAGE_TYPE_LEFT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == MESSAGE_TYPE_LEFT) {
            MessageViewHolderLeft(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.message_item_left, parent, false
                )
            )
        } else {
            MessageViewHolderRight(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.message_item_right, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) == MESSAGE_TYPE_LEFT) {
            (holder as MessageViewHolderLeft).onBind(position)
        } else {
            (holder as MessageViewHolderRight).onBind(position)
        }
    }

    override fun getItemCount(): Int = messageList?.size

    inner class MessageViewHolderLeft(private val binding: MessageItemLeftBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            binding.message = messageList[position]
            binding.executePendingBindings()
            // set show date and time
            if (binding.tvDate.text.toString() == DateUtils.getCurrentDate()) {
                binding.tvDate.visibility = View.GONE
                binding.tvTime.visibility = View.VISIBLE
            } else {
                binding.tvTime.visibility = View.GONE
                binding.tvDate.apply {
                    visibility = View.VISIBLE
                    text = binding.tvTime.text.toString() + " " + text.toString()
                }
            }
        }
    }

    inner class MessageViewHolderRight(private val binding: MessageItemRightBinding) :
        BaseViewHolder(binding.root) {

        override fun onBind(position: Int) {
            binding.message = messageList[position]
            binding.executePendingBindings()
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