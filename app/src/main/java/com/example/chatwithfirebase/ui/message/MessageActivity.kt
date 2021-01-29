package com.example.chatwithfirebase.ui.message

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivity
import com.example.chatwithfirebase.databinding.ActivityMessageBinding

import com.example.chatwithfirebase.di.ViewModelFactory
import javax.inject.Inject

class MessageActivity : BaseActivity<ActivityMessageBinding, MessageViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var messageViewModel: MessageViewModel

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
    }

}