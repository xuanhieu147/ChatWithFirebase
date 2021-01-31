package com.example.chatwithfirebase.ui.home.module

import com.example.chatwithfirebase.ui.home.adapter.UserAdapter
import dagger.Module
import dagger.Provides

@Module
class UserModule {

    @Provides
    fun provideUserAdapter() = UserAdapter()
}