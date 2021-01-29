package com.example.chatwithfirebase.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.data.model.User
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val liveDataUserList = MutableLiveData<List<User>>()

    fun getUserList(): MutableLiveData<List<User>> = liveDataUserList

    fun getData() {
        setLoading(true)
        compositeDisposable.add(
            firebaseDataRepository.getAllUser()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getListUserSuccess, this::errorGetDataListUser)
        )
    }

    private fun getListUserSuccess(userList: List<User>) {
        setLoading(false)
        liveDataUserList.value = userList
    }

    private fun errorGetDataListUser(t: Throwable) {
        setLoading(false)
        liveDataUserList.value = null
    }


}