package com.example.chatwithfirebase.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityBlack
import com.example.chatwithfirebase.databinding.ActivityChatBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import javax.inject.Inject

class ChatActivity : BaseActivityBlack<ActivityChatBinding, ChatViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var chatViewModel: ChatViewModel

    override fun getViewModel(): ChatViewModel {
        chatViewModel = ViewModelProvider(this, factory).get(ChatViewModel::class.java)
        return chatViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_chat

    override fun getBindingVariable(): Int = BR.chatViewModel

    override fun updateUI(savedInstanceState: Bundle?) {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

}