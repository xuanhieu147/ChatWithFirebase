package com.example.chatwithfirebase.ui.login

import android.os.Bundle
import android.util.Log.e
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityBlack
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.databinding.ActivityLoginBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.messages.MessagesActivity
import com.example.chatwithfirebase.ui.register.RegisterViewModel
import com.example.chatwithfirebase.ui.welcome.WelcomeActivity
import com.example.chatwithfirebase.utils.ToastUtils
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

        loginViewModel.uiEventLiveData.observe(this,{
            when(it){
                LoginViewModel.ERROR_TYPE_EMAIL->
                    ToastUtils.toastError(this,R.string.email,R.string.type_email)

                LoginViewModel.ERROR_EMAIL_FORMAT->
                    ToastUtils.toastWarning(this,R.string.email,R.string.wrong_email_format)

                LoginViewModel.ERROR_TYPE_PASSWORD->
                    ToastUtils.toastError(this,R.string.password,R.string.type_password)

                LoginViewModel.LOGIN_SUCCESS-> {
                    ToastUtils.toastSuccess(this, R.string.login, R.string.success)
                    goScreen(
                        MessagesActivity::class.java,
                        false, R.anim.slide_in_right, R.anim.slide_out_left
                    )
                }

                BaseViewModel.SHOW_ERROR ->
                    ToastUtils.toastError(this, R.string.wrong_email_password, loginViewModel.errorMessage)
            }
        })
    }
}