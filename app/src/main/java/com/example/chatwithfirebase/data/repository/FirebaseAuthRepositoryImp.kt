package com.example.chatwithfirebase.data.repository

import com.example.chatwithfirebase.data.remote.FirebaseAuthSource
import io.reactivex.Completable
import javax.inject.Inject

class FirebaseAuthRepositoryImp @Inject constructor(
    private val firebaseAuthSource: FirebaseAuthSource
     ) : FirebaseAuthRepository {

    override fun registerUser(
        email: String,
        password: String,
        fullName: String): Completable {
        return firebaseAuthSource.registerUser(email, password, fullName)
    }

    override fun login(email: String, password: String): Completable {
        return firebaseAuthSource.login(email, password)
    }

    override fun signOut() {
        return firebaseAuthSource.signOut()
    }
}