package com.example.chatwithfirebase.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.di.ViewModelKey
import com.example.chatwithfirebase.ui.message.MessageViewModel
import com.example.chatwithfirebase.ui.login.LoginViewModel
import com.example.chatwithfirebase.ui.home.HomeViewModel
import com.example.chatwithfirebase.ui.setting.notification.NotificationViewModel
import com.example.chatwithfirebase.ui.register.RegisterViewModel
import com.example.chatwithfirebase.ui.setting.SettingViewModel
import com.example.chatwithfirebase.ui.welcome.WelcomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    abstract fun welcomeViewModel(viewModel: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun registerViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun messageViewModel(viewModel: MessageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun settingViewModel(viewModel: SettingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun notificationViewModel(viewModel: NotificationViewModel): ViewModel
}

