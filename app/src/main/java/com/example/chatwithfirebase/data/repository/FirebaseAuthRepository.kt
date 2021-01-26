package com.example.chatwithfirebase.data.repository

import io.reactivex.Completable

interface FirebaseAuthRepository {

    fun registerUser(email: String, password: String, linkImage: String, username: String) : Completable

    fun login(email: String, password: String) : Completable

    fun signOut()
}