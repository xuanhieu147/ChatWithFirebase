package com.example.chatwithfirebase.ui.login

import android.os.Bundle
import android.util.Log.e
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityBlack
import com.example.chatwithfirebase.databinding.ActivityLoginBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import javax.inject.Inject

class LoginActivity : BaseActivityBlack<ActivityLoginBinding, LoginViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var loginViewModel: LoginViewModel

    override fun getViewModel(): LoginViewModel {
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
        return loginViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun updateUI(savedInstanceState: Bundle?) {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}