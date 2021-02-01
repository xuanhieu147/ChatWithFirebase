package com.example.chatwithfirebase.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivity
import com.example.chatwithfirebase.databinding.ActivityNotificationBinding
import com.example.chatwithfirebase.databinding.ActivitySettingBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.setting.SettingViewModel
import javax.inject.Inject

class NotificationActivity : BaseActivity<ActivityNotificationBinding, NotificationViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var notificationViewModel: NotificationViewModel

    override fun getViewModel(): NotificationViewModel {
        notificationViewModel = ViewModelProvider(this, factory).get(NotificationViewModel::class.java)
        return notificationViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_notification

    override fun getBindingVariable(): Int = BR.notificationViewModel

    override fun updateUI(savedInstanceState: Bundle?) {


    }

}