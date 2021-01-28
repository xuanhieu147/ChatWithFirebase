package com.example.chatwithfirebase.data.repository.auth

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable

interface FirebaseAuthRepository {

    fun getCurrentUserId(): String

    fun getCurrentUser() : FirebaseUser?

    fun registerUser(email: String, password: String, fullName: String) : Completable

    fun login(email: String, password: String) : Completable

    fun signOut()
}