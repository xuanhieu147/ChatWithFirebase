package com.example.chatwithfirebase.ui.welcome

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityBlack
import com.example.chatwithfirebase.databinding.ActivityWelcomeBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.login.LoginActivity
import com.example.chatwithfirebase.ui.messages.MessagesActivity
import com.example.chatwithfirebase.ui.register.RegisterActivity
import com.example.chatwithfirebase.ui.register.RegisterViewModel
import com.example.chatwithfirebase.utils.ToastUtils
import javax.inject.Inject

class WelcomeActivity : BaseActivityBlack<ActivityWelcomeBinding, WelcomeViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var welcomeViewModel: WelcomeViewModel


    override fun getViewModel(): WelcomeViewModel {
        welcomeViewModel = ViewModelProvider(this, factory).get(WelcomeViewModel::class.java)
        return welcomeViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun getBindingVariable(): Int = BR.welcomeViewModel

    override fun updateUI(savedInstanceState: Bundle?) {

        binding.btnCreateAccount.setOnClickListener {
            goScreen(
                RegisterActivity::class.java,
                false,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }

        binding.btnLogin.setOnClickListener {
            goScreen(LoginActivity::class.java, false, R.anim.slide_in_right, R.anim.slide_out_left)
        }

        welcomeViewModel.uiEventLiveData.observe(this, {
            if (it == WelcomeViewModel.LOGIN_SUCCESS) {

                goScreen(
                    MessagesActivity::class.java,
                    false, R.anim.slide_in_right, R.anim.slide_out_left
                )

            }
        })

    }

    override fun onStart() {
        super.onStart()
        welcomeViewModel.login()
    }
}