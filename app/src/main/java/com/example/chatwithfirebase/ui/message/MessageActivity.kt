package com.example.chatwithfirebase.ui.message

import android.os.Bundle
import android.util.Log.e
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityGradient
import com.example.chatwithfirebase.databinding.ActivityMessageBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.message.adapter.MessageAdapter
import com.example.chatwithfirebase.utils.DateUtils
import com.example.chatwithfirebase.utils.LogUtil
import javax.inject.Inject


class MessageActivity : BaseActivityGradient<ActivityMessageBinding, MessageViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var messageViewModel: MessageViewModel

    @Inject
    lateinit var messageAdapter: MessageAdapter

    override fun getViewModel(): MessageViewModel {
        messageViewModel = ViewModelProvider(this, factory).get(MessageViewModel::class.java)
        return messageViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_message

    override fun getBindingVariable(): Int = BR.messageViewModel

    override fun updateUI(savedInstanceState: Bundle?) {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        // get info Receiver
        messageViewModel.getInfoReceiver(getIdReceiver()!!)
        messageViewModel.liveDataGetInfoReceiver().observe(this, {
            if (it != null) {
                binding.user = it
            } else {
                toast(resources.getString(R.string.error_get_data))
            }
        })

        // get all message
        binding.rvChat.apply {
            setHasFixedSize(true)
            adapter = messageAdapter
        }

        messageViewModel.getAllMessage(getIdReceiver()!!)
        messageViewModel.liveDataGetAllMessage().observe(this, {
            if (it != null) {
                messageAdapter.clearItems()
                messageAdapter.addItems(it)

                // scroll last position
                binding.rvChat.scrollToPosition(it.size - 1)

                // update last message and time
                messageViewModel.updateLastMessageAndTime(
                    getIdReceiver()!!,
                    it[it.size - 1].message,
                    it[it.size - 1].date,
                    it[it.size - 1].time)

            } else {
                toast(resources.getString(R.string.error_get_data))
            }
        })

        // send message
        binding.imgSend.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            messageViewModel.sendMessage(getIdReceiver()!!, message,getAvatarSender()!!,"")

            // clear text
            binding.edtMessage.text?.clear()

            // auto scroll last position when the layout size changes
            binding.rvChat.apply{
                addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                scrollToPosition(messageAdapter.itemCount - 1)
            }
            }
        }
    }

}