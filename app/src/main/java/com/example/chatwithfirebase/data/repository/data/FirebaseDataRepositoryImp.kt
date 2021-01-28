package com.example.chatwithfirebase.data.repository.data

import android.net.Uri
import com.example.chatwithfirebase.data.model.Chat
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.data.remote.FirebaseDataSource
import com.google.firebase.database.DataSnapshot
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FirebaseDataRepositoryImp @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource) : FirebaseDataRepository {

    override fun getAllUser(): Observable<List<User>> {
        return firebaseDataSource.getAllUser()
    }

    override fun getAllMessage(senderId: String, receiverId: String): Observable<List<Chat>> {
        return firebaseDataSource.getAllMessage(senderId,receiverId)
    }

    override fun sendMessage(senderId: String, receiverId: String, message: String): Completable {
       return firebaseDataSource.sendMessage(senderId,receiverId,message)
    }

    override fun uploadImageProfile(filePath: Uri): Completable {
        return firebaseDataSource.uploadImageProfile(filePath)
    }

    override fun getCurrentUserId(): String {
        return firebaseDataSource.getCurrentUserId()
    }
}