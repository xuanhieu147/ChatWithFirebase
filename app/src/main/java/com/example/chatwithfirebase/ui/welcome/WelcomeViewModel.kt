package com.example.chatwithfirebase.ui.welcome

import android.content.Intent
import com.example.chatwithfirebase.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class WelcomeViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        // success
        const val AUTO_LOGIN = 9
    }

    fun checkUserAndAutoLogin() {
        if (!sharedPreferencesManager.getUser().isNullOrEmpty()) {
            setLoading(true)
            uiEventLiveData.value = AUTO_LOGIN
        }
    }
}