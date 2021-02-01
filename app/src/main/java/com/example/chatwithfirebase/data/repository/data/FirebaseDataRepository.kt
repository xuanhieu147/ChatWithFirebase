package com.example.chatwithfirebase.data.repository.data

import android.net.Uri
import com.example.chatwithfirebase.data.model.Message
import com.example.chatwithfirebase.data.model.User
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.ArrayList

interface FirebaseDataRepository {

    fun getAllUser():Observable<ArrayList<User>>

    fun searchForUser(str: String):Observable<ArrayList<User>>

    fun getAllMessage(receiverId: String):Observable<ArrayList<Message>>

    fun sendMessage(receiverId: String, message: String,avatarSender:String,imageUpload:String):Completable

    fun sendImageMessage(receiverId: String, avatarSender:String,imageUpload:String):Completable

    fun updateLastMessageAndTime(userId:String,lastMessage:String,date:String,time:String): Completable

    fun uploadImageProfile(filePath: Uri) : Completable

    fun getInfoReceiver(userId:String) : Observable<User>

    fun getInfoUser():Observable<User>

    fun getCurrentUserId():String

    fun getCurrentUser():FirebaseUser?
}