package com.example.chatwithfirebase.data.repository.data

import com.example.chatwithfirebase.data.model.User
import io.reactivex.Observable

interface FirebaseDataRepository {

    fun getAllUserList():Observable<List<User>>
}