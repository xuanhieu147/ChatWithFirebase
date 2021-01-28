package com.example.chatwithfirebase.data.repository.data

import com.example.chatwithfirebase.data.model.User
import com.google.firebase.database.DataSnapshot
import io.reactivex.Observable

interface FirebaseDataRepository {

    fun getAllUser():Observable<List<User>>

    fun getAllMessage():Observable<DataSnapshot>

    fun getCurrentUserId():String
}