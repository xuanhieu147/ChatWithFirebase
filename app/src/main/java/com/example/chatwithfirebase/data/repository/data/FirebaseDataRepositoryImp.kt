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
    private val firebaseDataSource: FirebaseDataSource) : FirebaseDataRepository {

    override fun getAllUser(): Observable<ArrayList<User>> {
        return firebaseDataSource.getAllUser()
    }

    override fun getAllUserChatted(): Observable<ArrayList<User>> {
       return firebaseDataSource.getAllUserChatted()
    }

    override fun searchForUser(str: String): Observable<ArrayList<User>> {
        return firebaseDataSource.searchForUser(str)

    }

    override fun getAllMessage(receiverId: String): Observable<ArrayList<Message>> {
        return firebaseDataSource.getAllMessage(receiverId)
    }

    override fun sendMessage(receiverId: String, message: String,avatarSender:String,imageUpload:String): Completable {
       return firebaseDataSource.sendMessage(receiverId,message,avatarSender,imageUpload)
    }

    override fun updateLastMessageAndTime(userId: String, lastMessage: String,date:String ,time: String): Completable {
        return firebaseDataSource.updateLastMessageAndTime(userId,lastMessage,date,time)
    }

    override fun uploadImageProfile(filePath: Uri): Completable {
        return firebaseDataSource.uploadImageProfile(filePath)
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
}