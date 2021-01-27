package com.example.chatwithfirebase.ui.messages

import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.data.model.User
import com.google.firebase.database.DataSnapshot
import javax.inject.Inject

class MessagesViewModel @Inject constructor() : BaseViewModel() {

    fun getDataSnapshot() {
        compositeDisposable.add(
            firebaseDataRepository.getAllUser()
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getDataSnapshotSuccess, this::errorDataSnapshot)
        )
    }

    private fun getDataSnapshotSuccess(snapshot: DataSnapshot) {
        for (dataSnapshot: DataSnapshot in snapshot.children) {
            val user = snapshot.getValue(User::class.java)
            user?.let {
                if (user.userId != firebaseDataRepository.getCurrentUserId()) {

                }
            }

        }
    }

    private fun errorDataSnapshot(t: Throwable) {

    }


}