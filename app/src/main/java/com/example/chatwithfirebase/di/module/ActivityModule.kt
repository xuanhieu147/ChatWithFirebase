package com.example.chatwithfirebase.di.module

import com.example.chatwithfirebase.ui.home.module.UserModule
import com.example.chatwithfirebase.ui.message.MessageActivity
import com.example.chatwithfirebase.ui.login.LoginActivity
import com.example.chatwithfirebase.ui.home.HomeActivity
import com.example.chatwithfirebase.ui.message.module.MessageModule
import com.example.chatwithfirebase.ui.notification.NotificationActivity
import com.example.chatwithfirebase.ui.notification.NotificationViewModel
import com.example.chatwithfirebase.ui.register.RegisterActivity
import com.example.chatwithfirebase.ui.setting.SettingActivity
import com.example.chatwithfirebase.ui.welcome.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Duc Minh
 */

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    abstract fun contributeWelcomeActivity(): WelcomeActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [UserModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [MessageModule::class])
    abstract fun contributeMessageActivity(): MessageActivity

    @ContributesAndroidInjector
    abstract fun contributeSettingActivity(): SettingActivity

    @ContributesAndroidInjector
    abstract fun contributeNotificationActivity(): NotificationActivity
}