package com.example.chatwithfirebase.di.component

import com.example.chatwithfirebase.di.ChatWithFirebase
import com.example.chatwithfirebase.di.module.ActivityModule
import com.example.chatwithfirebase.di.module.ApiModule
import com.example.chatwithfirebase.di.module.AppModule
import com.example.chatwithfirebase.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
        modules = [(AndroidInjectionModule::class),
            (ApiModule::class),
            (ActivityModule::class),
            (ViewModelModule::class),
            (AppModule::class)
        ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: ChatWithFirebase): Builder

        fun build(): AppComponent
    }

    /*
     * This is our custom Application class
     * */
    fun inject(chatWithFirebase: ChatWithFirebase)
}