package com.example.chatwithfirebase.ui.message.module

import com.example.chatwithfirebase.data.repository.data.FirebaseDataRepository
import com.example.chatwithfirebase.ui.home.adapter.UserAdapter
import com.example.chatwithfirebase.ui.message.adapter.MessageAdapter
import dagger.Module
import dagger.Provides


@Module
class MessageModule {

    @Provides
    fun provideMessageAdapter(
        firebaseDataRepository: FirebaseDataRepository) = MessageAdapter(firebaseDataRepository)
}