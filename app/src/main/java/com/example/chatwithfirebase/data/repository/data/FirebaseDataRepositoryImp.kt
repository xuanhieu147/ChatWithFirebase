package com.example.chatwithfirebase.data.repository.data

import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.data.remote.FirebaseDataSource
import io.reactivex.Observable
import javax.inject.Inject

class FirebaseDataRepositoryImp @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource) : FirebaseDataRepository {

    override fun getAllUserList(): Observable<List<User>> {
        return firebaseDataSource.getAllUserList()
    }
}