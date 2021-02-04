package com.example.chatwithfirebase.data.remote

import com.example.chatwithfirebase.base.constants.Constants
import com.example.chatwithfirebase.utils.LogUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

/** Code by Duc Minh */
class FirebaseAuthSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase) {

    private val notValue = "Not Value"

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun getCurrentUserId(): String {
        if (firebaseAuth.currentUser!!.uid != null) {
            return firebaseAuth.currentUser!!.uid
        }
        return notValue
    }

    // Completable notify status success or failed
    fun registerUser(email: String, password: String, fullName: String): Completable {
        return Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (getCurrentUserId() != notValue) {
                    // information user
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["userId"] = getCurrentUserId()
                    hashMap["email"] = email
                    hashMap["password"] = password
                    hashMap["avatarUser"] = Constants.AVATAR_DEFAULT_USER
                    hashMap["fullName"] = fullName
                    hashMap["status"] = "offline"

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
                    LogUtil.error("Err", "Firebase userId not value, register failed")
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
                }.addOnFailureListener { emitter.onError(it) }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}