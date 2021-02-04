package com.example.chatwithfirebase.data.repository.data

import android.net.Uri
import com.example.chatwithfirebase.data.model.Message
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.data.remote.FirebaseDataSource
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.ArrayList
import javax.inject.Inject

class FirebaseDataRepositoryImp @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
    ) : FirebaseDataRepository {

    override fun getAllUser(): Observable<ArrayList<User>> {
        return firebaseDataSource.getAllUser()
    }

    override fun getAllMessage(receiverId: String): Observable<ArrayList<Message>> {
        return firebaseDataSource.getAllMessage(receiverId)
    }

    override fun sendMessage(receiverId: String, message: String, avatarSender: String): Completable {
        return firebaseDataSource.sendMessage(receiverId, message, avatarSender)
    }

    override fun sendImageMessage(fileUri: Uri, receiverId: String, avatarSender: String): Completable {
        return firebaseDataSource.sendImageMessage(fileUri, receiverId, avatarSender)
    }

    override fun getInfoReceiver(userId: String): Observable<User> {
        return firebaseDataSource.getInfoReceiver(userId)
    }

    override fun getInfoUser(): Observable<User> {
        return firebaseDataSource.getInfoUser()
    }

    override fun getCurrentUserId(): String {
        return firebaseDataSource.getCurrentUserId()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseDataSource.getCurrentUser()
    }

    override fun searchForUser(str: String): Observable<ArrayList<User>> {
        return firebaseDataSource.searchForUser(str)
    }

    override fun updateStatusUser(status: String): Completable {
        return firebaseDataSource.updateStatusUser(status)
    }

    override fun updateFullName(fullname: String): Completable {
        return firebaseDataSource.updateFullName(fullname)
    }

    override fun uploadImageProfile(filePath: Uri): Completable {
        return firebaseDataSource.uploadImageProfile(filePath)
    }
}