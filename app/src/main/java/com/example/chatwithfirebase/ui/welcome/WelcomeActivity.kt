package com.example.chatwithfirebase.ui.welcome

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivity
import com.example.chatwithfirebase.databinding.ActivityWelcomeBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.login.LoginActivity
import com.example.chatwithfirebase.ui.home.HomeActivity
import com.example.chatwithfirebase.ui.register.RegisterActivity
import javax.inject.Inject

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding, WelcomeViewModel>() {

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
            if (it == WelcomeViewModel.AUTO_LOGIN) {
                Handler().postDelayed({
                    autoLogin()
                }, 2000)
            }
        })

    }

    private fun autoLogin() {
        welcomeViewModel.setLoading(false)
        goScreen(
            HomeActivity::class.java,
            true, R.anim.slide_in_right, R.anim.slide_out_left
        )
    }

    override fun onStart() {
        super.onStart()
        welcomeViewModel.checkUserAndAutoLogin()
    }
}