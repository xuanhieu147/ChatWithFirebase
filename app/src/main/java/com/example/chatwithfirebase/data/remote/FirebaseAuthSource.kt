package com.example.chatwithfirebase.data.remote

import android.util.Log.e
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.constants.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class FirebaseAuthSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) {

    private val notValue = "Not value"

    // get current user
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    // get current userId
    fun getCurrentUserId(): String {
        if (firebaseAuth.currentUser!!.uid != null) {
            return firebaseAuth.currentUser!!.uid
        }
        return notValue
    }

    // Completable notify status success or fall
    fun registerUser(email: String, password: String, fullName: String): Completable {
        return Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (getCurrentUserId() != notValue) {
                        val hashMap: HashMap<Any, Any> = HashMap()
                        hashMap["userId"] = getCurrentUserId()
                        hashMap["email"] = email
                        hashMap["password"] = password
                        hashMap["linkImage"] = Constants.AVATAR_DEFAULT_USER
                        hashMap["fullName"] = fullName
                        hashMap["online"] = "true"

                        // add user
                        firebaseDatabase.reference
                            .child("User")
                            .child(getCurrentUserId())
                            .setValue(hashMap)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    emitter.onComplete()
                                }
                            }
                            .addOnFailureListener { e -> emitter.onError(e) }
                    } else {
                        e("Err", "Firebase User Id not value")
                    }
                }
                .addOnFailureListener { e -> emitter.onError(e) }
        }
    }


    fun login(email: String, password: String): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    }
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

}