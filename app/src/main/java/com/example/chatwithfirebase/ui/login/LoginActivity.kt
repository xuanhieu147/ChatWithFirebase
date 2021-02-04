package com.example.chatwithfirebase.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivity
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.databinding.ActivityLoginBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.home.HomeActivity
import com.example.chatwithfirebase.utils.ToastUtils
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

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

        loginViewModel.getEmail()
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
                    openLoginScreen()
                }

                BaseViewModel.SHOW_ERROR ->
                    ToastUtils.toastError(this, R.string.wrong_email_password, loginViewModel.errorMessage)
            }
        })
    }

    private fun openLoginScreen(){
        goScreen(
            HomeActivity::class.java,
            false,
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
    }
}