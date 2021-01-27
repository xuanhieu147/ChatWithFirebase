package com.example.chatwithfirebase.data.repository.auth

import com.example.chatwithfirebase.data.remote.FirebaseAuthSource
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import javax.inject.Inject

class FirebaseAuthRepositoryImp @Inject constructor(
    private val firebaseAuthSource: FirebaseAuthSource
     ) : FirebaseAuthRepository {

    override fun getCurrentUser(): FirebaseUser? {
       return firebaseAuthSource.getCurrentUser()
    }

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
         firebaseAuthSource.signOut()
    }
}