package com.example.chatwithfirebase.ui.register

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivity
import com.example.chatwithfirebase.base.BaseViewModel.Companion.SHOW_ERROR
import com.example.chatwithfirebase.databinding.ActivityRegisterBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.login.LoginActivity
import com.example.chatwithfirebase.utils.ToastUtils
import javax.inject.Inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var registerViewModel: RegisterViewModel

    override fun getViewModel(): RegisterViewModel {
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
        return registerViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun getBindingVariable(): Int = BR.registerViewModel

    override fun updateUI(savedInstanceState: Bundle?) {
        binding.imgBack.setOnClickListener{
            onBackPressed()
        }

        registerViewModel.uiEventLiveData.observe(this,{
            when(it){
            RegisterViewModel.ERROR_TYPE_EMAIL->
                ToastUtils.toastError(this,R.string.email,R.string.type_email)

                RegisterViewModel.ERROR_EMAIL_FORMAT->
                    ToastUtils.toastWarning(this,R.string.email,R.string.wrong_email_format)

                RegisterViewModel.ERROR_TYPE_PASSWORD->
                    ToastUtils.toastError(this,R.string.password,R.string.type_password)

                RegisterViewModel.ERROR_RE_PASSWORD->
                    ToastUtils.toastError(this,R.string.password,R.string.re_type_password)

                RegisterViewModel.ERROR_RE_PASSWORD_NOT_SAME_PASSWORD->
                    ToastUtils.toastError(this,R.string.error,R.string.re_password_not_same_password)

                RegisterViewModel.ERROR_TYPE_FULL_NAME->
                    ToastUtils.toastError(this,R.string.full_name,R.string.type_full_name)

                RegisterViewModel.REGISTER_SUCCESS-> {
                    ToastUtils.toastSuccess(this, R.string.login, R.string.success)
                    goScreen(
                        LoginActivity::class.java,
                        false, R.anim.slide_in_right, R.anim.slide_out_left
                    )
                }

                SHOW_ERROR ->  ToastUtils.toastError(this, R.string.error, registerViewModel.errorMessage)
            }
        })
    }
}