package com.example.chatwithfirebase.data.repository.data

import android.net.Uri
import com.example.chatwithfirebase.data.model.Chat
import com.example.chatwithfirebase.data.model.User
import com.google.firebase.database.DataSnapshot
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface FirebaseDataRepository {

    fun getAllUser():Observable<List<User>>

    fun getAllMessage(senderId: String, receiverId: String):Observable<List<Chat>>

    fun sendMessage(senderId: String, receiverId: String, message: String):Completable

    fun uploadImageProfile(filePath: Uri) : Completable

    fun getCurrentUserId():String
}