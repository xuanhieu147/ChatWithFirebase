package com.example.chatwithfirebase.di.module

import com.example.chatwithfirebase.ui.chat.ChatActivity
import com.example.chatwithfirebase.ui.login.LoginActivity
import com.example.chatwithfirebase.ui.messages.MessagesActivity
import com.example.chatwithfirebase.ui.register.RegisterActivity
import com.example.chatwithfirebase.ui.welcome.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    abstract fun contributeWelcomeActivity(): WelcomeActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeMessagesActivity(): MessagesActivity

    @ContributesAndroidInjector
    abstract fun contributeChatActivity(): ChatActivity
}