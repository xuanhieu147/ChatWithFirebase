package com.example.chatwithfirebase.ui.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityBlack
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.databinding.ActivityMessagesBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.welcome.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MessagesActivity : BaseActivityBlack<ActivityMessagesBinding, MessagesViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var messagesViewModel: MessagesViewModel

    override fun getViewModel(): MessagesViewModel {
        messagesViewModel = ViewModelProvider(this, factory).get(MessagesViewModel::class.java)
        return messagesViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_messages

    override fun getBindingVariable(): Int = BR.messageViewModel

    override fun updateUI(savedInstanceState: Bundle?) {
        binding.imgSetting.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
            goScreen(
                WelcomeActivity::class.java,
                false,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }

    }

}