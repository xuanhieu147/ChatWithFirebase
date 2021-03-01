package com.example.chatwithfirebase.base

import androidx.lifecycle.ViewModel
import com.example.chatwithfirebase.base.manager.SharedPreferencesManager
import com.example.chatwithfirebase.data.repository.FirebaseNotificationRepository
import com.example.chatwithfirebase.data.repository.auth.FirebaseAuthRepository
import com.example.chatwithfirebase.data.repository.data.FirebaseDataRepository
import com.example.chatwithfirebase.di.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseViewModel() : ViewModel() {

    companion object{
        const val SHOW_ERROR = 1
    }

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var firebaseAuthRepository: FirebaseAuthRepository

    @Inject
    lateinit var firebaseDataRepository: FirebaseDataRepository

    @Inject
    lateinit var firebaseNotificationRepository: FirebaseNotificationRepository

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mIsLoading = SingleLiveData<Boolean>()
    val uiEventLiveData = SingleLiveData<Int>()
    var errorMessage: Any = "Unknown error"

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun launch(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    fun isLoading(): Boolean{
        return mIsLoading.value ?: false
    }

    fun getLoading(): SingleLiveData<Boolean>? {
        return mIsLoading
    }

    fun setLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

    fun showError(message: Any){
        errorMessage = message
        uiEventLiveData.value = SHOW_ERROR
    }

    open fun onStart(){}
    open fun onStop(){}
    open fun onResume(){}
}