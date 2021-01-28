package com.example.chatwithfirebase.data.repository.data

import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.data.remote.FirebaseDataSource
import com.google.firebase.database.DataSnapshot
import io.reactivex.Observable
import javax.inject.Inject

class FirebaseDataRepositoryImp @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource) : FirebaseDataRepository {

    override fun getAllUser(): Observable<List<User>> {
        return firebaseDataSource.getAllUser()
    }

    override fun getAllMessage(): Observable<DataSnapshot> {
        return firebaseDataSource.getAllMessage()
    }

    override fun getCurrentUserId(): String {
        return firebaseDataSource.getCurrentUserId()
    }
}