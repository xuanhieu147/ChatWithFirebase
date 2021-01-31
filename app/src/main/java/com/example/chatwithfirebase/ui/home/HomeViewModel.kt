package com.example.chatwithfirebase.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.base.SingleLiveData
import com.example.chatwithfirebase.data.model.User
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val liveDataUserList = MutableLiveData<List<User>>()
    private val liveDataInfoReceiver = SingleLiveData<User>()
    private val liveDataInfoUser = MutableLiveData<User>()

    // get all user
    fun getUserList(): MutableLiveData<List<User>> = liveDataUserList
    fun getAllUser() {
        setLoading(true)
        compositeDisposable.add(
            firebaseDataRepository.getAllUser()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getAllUserSuccess, this::getAllUserError)
        )
    }

    private fun getAllUserSuccess(userList: List<User>) {
        setLoading(false)
        liveDataUserList.value = userList
    }

    private fun getAllUserError(t: Throwable) {
        setLoading(false)
        liveDataUserList.value = null
    }

    // get info receiver when item click
    fun getInfoReceiver(): MutableLiveData<User> = liveDataInfoReceiver
    fun onItemClickGetPositionUser(position: Int) {
        liveDataUserList.value?.let {
            liveDataInfoReceiver.value = it[position]
        }
    }

    // get info user
    fun getUser():MutableLiveData<User> = liveDataInfoUser
    fun getInfoUser() {
        setLoading(true)
        compositeDisposable.add(
            firebaseDataRepository.getInfoUser()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getInfoUserSuccess, this::getInfoUserSuccess)
        )
    }

    private fun getInfoUserSuccess(user: User) {
        liveDataInfoUser.value = user
    }

    private fun getInfoUserSuccess(t: Throwable) {
        liveDataInfoUser.value = null
    }

}