package com.example.chatwithfirebase.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithfirebase.base.constants.Constants
import com.example.chatwithfirebase.base.manager.SharedPreferencesManager
import com.example.chatwithfirebase.data.remote.FirebaseAuthSource
import com.example.chatwithfirebase.data.remote.FirebaseDataSource
import com.example.chatwithfirebase.data.repository.auth.FirebaseAuthRepository
import com.example.chatwithfirebase.data.repository.auth.FirebaseAuthRepositoryImp
import com.example.chatwithfirebase.data.repository.data.FirebaseDataRepository
import com.example.chatwithfirebase.data.repository.data.FirebaseDataRepositoryImp
import com.example.chatwithfirebase.di.ChatWithFirebase
import com.example.chatwithfirebase.di.rx.SchedulerProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
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
        return AndroidSchedulers.mainThread() }

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider()

    @Provides
    @Singleton
    fun provideContext(app: ChatWithFirebase): Context = app


    @Provides
    @Named("vertical")
    fun provideVerticalLinearLayoutManager(context: Context) =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    @Provides
    @Named("horizontal")
    fun provideHorizontalLinearLayoutManager(context: Context) =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(context: Context) = SharedPreferencesManager(context)

    /**
    Provide Firebase
     */

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    //    @Singleton
    //    @Provides
    //    fun provideFirebaseUser() = FirebaseAuth.getInstance().currentUser!!

    @Singleton
    @Provides
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance(Constants.FIREBASE_DATABASE_URL)

    @Singleton
    @Provides
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuthSource(firebaseAuth: FirebaseAuth,
                                  firebaseDatabase: FirebaseDatabase) = FirebaseAuthSource(firebaseAuth, firebaseDatabase)

    @Singleton
    @Provides
    fun provideFirebaseDataSource(firebaseAuth: FirebaseAuth,
                                  firebaseDatabase: FirebaseDatabase,
                                  firebaseStorage: FirebaseStorage) = FirebaseDataSource(firebaseAuth, firebaseDatabase,firebaseStorage)

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(
        firebaseAuthRepositoryImp: FirebaseAuthRepositoryImp
    ): FirebaseAuthRepository = firebaseAuthRepositoryImp

    @Provides
    @Singleton
    fun provideFirebaseDataRepository(
        firebaseDataRepositoryImp: FirebaseDataRepositoryImp
    ): FirebaseDataRepository = firebaseDataRepositoryImp

}