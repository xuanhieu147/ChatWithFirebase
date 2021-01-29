package com.example.chatwithfirebase.ui.home.module

import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.ui.home.adapter.HomeAdapter
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun provideHomeAdapter() = HomeAdapter()
}