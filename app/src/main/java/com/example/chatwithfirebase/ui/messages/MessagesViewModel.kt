package com.example.chatwithfirebase.ui.messages

import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.data.model.User
import com.google.firebase.database.DataSnapshot
import javax.inject.Inject

class MessagesViewModel @Inject constructor() : BaseViewModel() {
    private val liveDataUserList = MutableLiveData<List<User>>()

    fun getUserList(): MutableLiveData<List<User>> = liveDataUserList

    fun getData() {
        compositeDisposable.add(
            firebaseDataRepository.getAllUser()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getListUserSuccess, this::errorGetDataListUser)
        )
    }

    private fun getListUserSuccess(userList: List<User>) {
            liveDataUserList.value = userList
    }

    private fun errorGetDataListUser(t: Throwable) {

    }


}