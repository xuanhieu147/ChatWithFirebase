package com.example.chatwithfirebase.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithfirebase.di.ChatWithFirebase
import com.example.chatwithfirebase.di.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider()

    @Provides
    @Singleton
    fun provideContext(app: ChatWithFirebase): Context = app

    //for list
    @Provides
    @Named("vertical")
    fun provideVerticalLinearLayoutManager(context: Context) =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    @Provides
    @Named("horizontal")
    fun provideHorizontalLinearLayoutManager(context: Context) =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

}