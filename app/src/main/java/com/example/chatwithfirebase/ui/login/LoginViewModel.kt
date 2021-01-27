package com.example.chatwithfirebase.ui.login

import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.utils.EmailUtil
import javax.inject.Inject

class LoginViewModel @Inject constructor(): BaseViewModel() {

    companion object {
        // error
        const val ERROR_EMAIL_FORMAT = 10
        const val ERROR_TYPE_EMAIL = 9
        const val ERROR_TYPE_PASSWORD = 8

        // success
        const val LOGIN_SUCCESS = 7
    }

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun loginUser() {
        setLoading(true)
        when {
            email.value.isNullOrEmpty() -> {
                setLoading(false)
                uiEventLiveData.value = ERROR_TYPE_EMAIL
            }

            !EmailUtil.checkValidEmail(email.value) -> {
                setLoading(false)
                uiEventLiveData.value = ERROR_EMAIL_FORMAT
            }

            password.value.isNullOrEmpty() -> {
                setLoading(false)
                uiEventLiveData.value = ERROR_TYPE_PASSWORD
            }

            else -> {
                compositeDisposable.add(
                    firebaseAuthRepository.login(email.value!!,password.value!!)
                        .compose(schedulerProvider.ioToMainCompletableScheduler())
                        .subscribe(this::onLoginSuccessful, this::onLoginFailed))
            }
        }
    }

    private fun onLoginSuccessful(){
        setLoading(false)
        uiEventLiveData.value = LOGIN_SUCCESS
    }

    private fun onLoginFailed(t:Throwable){
        setLoading(false)
        showError(t)
    }
}