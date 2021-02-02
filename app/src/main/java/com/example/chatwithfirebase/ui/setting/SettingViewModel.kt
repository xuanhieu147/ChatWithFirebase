package com.example.chatwithfirebase.ui.setting

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.ui.register.RegisterViewModel
import com.example.chatwithfirebase.utils.LogUtil
import com.example.chatwithfirebase.utils.ToastUtils
import javax.inject.Inject

class SettingViewModel @Inject constructor() : BaseViewModel() {

    companion object {

        const val SAME_FULL_NAME = 5
        const val SUCCESS = 6

    }

    private val liveDataInfoUser = MutableLiveData<User>()

    // get info user
    fun getUser(): MutableLiveData<User> = liveDataInfoUser
    fun getInfoUser() {
        setLoading(true)
        compositeDisposable.add(
            firebaseDataRepository.getInfoUser()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getInfoUserSuccess, this::getInfoUserError)
        )
    }

    // update Avatar
    fun updateAvatar(fileUri: Uri) {
        setLoading(true)
        compositeDisposable.add(
            firebaseDataRepository.uploadImageProfile(fileUri)
                .compose(schedulerProvider.ioToMainCompletableScheduler())
                .subscribe(this::updateAvatarSuccess, this::updateAvatarError)
        )

    }

    // update FullName
    fun updateFullName(fullName: String) {
        setLoading(true)
        if (fullName == liveDataInfoUser.value!!.fullName)
        {
            uiEventLiveData.value = SettingViewModel.SAME_FULL_NAME
        }
        compositeDisposable.add(
            firebaseDataRepository.updateFullName(fullName)
                .compose(schedulerProvider.ioToMainCompletableScheduler())
                .subscribe(this::updateAvatarSuccess, this::updateAvatarError)
        )

    }

    private fun getInfoUserSuccess(user: User) {
        setLoading(false)
        liveDataInfoUser.value = user
    }

    private fun getInfoUserError(t: Throwable) {
        liveDataInfoUser.value = null
    }

    private fun updateAvatarSuccess() {
        setLoading(false)
        LogUtil.error("Success")
    }

    private fun updateAvatarError(t: Throwable) {
        LogUtil.error(t.toString())
    }
}