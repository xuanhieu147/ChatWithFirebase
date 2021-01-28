package com.example.chatwithfirebase.data.remote

import com.example.chatwithfirebase.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Emitter
import javax.inject.Inject
import io.reactivex.Observable
import java.lang.reflect.Array
import java.util.ArrayList

class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage) {

    private val userList = ArrayList<User>()
    private val chatList = ArrayList<User>()


    // get current user
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    // get current userId
    fun getCurrentUserId():String = firebaseAuth.currentUser!!.uid

    // get all user
    fun getAllUser(): Observable<List<User>> {
        return Observable.create { emitter ->
            firebaseDatabase.reference.child("User")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userList.clear()
                        for (dataSnapshot: DataSnapshot in snapshot.children) {
                            val user = dataSnapshot.getValue(User::class.java)
                            user?.let {
                                if (user.userId != getCurrentUserId()) {
                                    userList.add(it)
                                    emitter.onNext(userList)
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }
                })
        }
    }

    // get all message
    fun getAllMessage() : Observable<DataSnapshot> {
            return Observable.create { emitter ->
                firebaseDatabase.reference.child("Chat")
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            emitter.onNext(snapshot)
                            emitter.onComplete()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            emitter.onError(error.toException())
                        }
                    })
            }

    }

}