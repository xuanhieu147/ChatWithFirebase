package com.example.chatwithfirebase.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivity
import com.example.chatwithfirebase.databinding.ActivityRegisterBinding
import com.example.chatwithfirebase.databinding.ActivitySettingBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.login.LoginActivity
import com.example.chatwithfirebase.ui.notification.NotificationActivity
import com.example.chatwithfirebase.ui.register.RegisterViewModel
import com.example.chatwithfirebase.ui.welcome.WelcomeActivity
import javax.inject.Inject

class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var settingViewModel: SettingViewModel

    override fun getViewModel(): SettingViewModel {
        settingViewModel = ViewModelProvider(this, factory).get(SettingViewModel::class.java)
        return settingViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun getBindingVariable(): Int = BR.settingViewModel

    override fun updateUI(savedInstanceState: Bundle?) {

        binding.rlNotifications.setOnClickListener {
            goScreen(
                NotificationActivity::class.java,
                false, R.anim.slide_in_right, R.anim.slide_out_left
            )
        }

        binding.rlLogOut.setOnClickListener {

        }
    }

}