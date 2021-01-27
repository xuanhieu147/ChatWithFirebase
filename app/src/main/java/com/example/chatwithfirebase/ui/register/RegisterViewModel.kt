package com.example.chatwithfirebase.ui.register

import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.utils.EmailUtil
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        // error
        const val ERROR_EMAIL_FORMAT = 10
        const val ERROR_TYPE_EMAIL = 9
        const val ERROR_TYPE_PASSWORD = 8
        const val ERROR_RE_PASSWORD = 7
        const val ERROR_RE_PASSWORD_NOT_SAME_PASSWORD = 6
        const val ERROR_TYPE_FULL_NAME = 5

        // success
        const val REGISTER_SUCCESS = 4
    }

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val rePassword = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()

    fun registerUser() {
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

            rePassword.value.isNullOrEmpty() -> {
                setLoading(false)
                uiEventLiveData.value = ERROR_RE_PASSWORD
            }

           !rePassword.value.equals(password.value) -> {
                setLoading(false)
                uiEventLiveData.value = ERROR_RE_PASSWORD_NOT_SAME_PASSWORD
            }

            fullName.value.isNullOrEmpty() -> {
                setLoading(false)
                uiEventLiveData.value = ERROR_TYPE_FULL_NAME
            }

            else -> {
                compositeDisposable.add(
                    firebaseAuthRepository.registerUser(email.value!!,password.value!!,fullName.value!!)
                    .compose(schedulerProvider.ioToMainCompletableScheduler())
                    .subscribe(this::onRegisterSuccessful, this::onRegisterFailed))
            }
        }
    }

    private fun onRegisterSuccessful(){
           setLoading(false)
           uiEventLiveData.value = REGISTER_SUCCESS
    }

    private fun onRegisterFailed(t:Throwable){
        setLoading(false)
        showError(t)
    }
}