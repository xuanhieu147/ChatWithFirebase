package com.example.chatwithfirebase.data.remote

import android.net.Uri
import com.example.chatwithfirebase.data.model.Chat
import com.example.chatwithfirebase.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import io.reactivex.Emitter
import io.reactivex.Flowable
import javax.inject.Inject
import io.reactivex.Observable
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.HashMap

class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage) {

    private val userList = ArrayList<User>()
    private val chatList = ArrayList<Chat>()

    private val notValue = "Not value"
    private val fileName = "image" + UUID.randomUUID().toString()


    // get current user
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    // get current userId
    fun getCurrentUserId(): String {
        if (firebaseAuth.currentUser!!.uid != null) {
            return firebaseAuth.currentUser!!.uid
        }
        return notValue
    }

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

    // get all message follow people chat
    fun getAllMessage(senderId: String, receiverId: String): Observable<List<Chat>> {
        return Observable.create { emitter ->
            firebaseDatabase.reference.child("Chat")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        chatList.clear()
                        for (dataSnapshot: DataSnapshot in snapshot.children) {
                            val chat = dataSnapshot.getValue(Chat::class.java)
                            chat?.let {
                                if (chat.senderId == senderId && chat.receiverId == receiverId ||
                                    chat.senderId == receiverId && chat.receiverId == senderId
                                ) {
                                    chatList.add(it)
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

    fun sendMessage(senderId: String, receiverId: String, message: String): Completable {
        return Completable.create { emitter ->
            // add chat message
            val hashMap: HashMap<Any, Any> = HashMap()
            hashMap["senderId"] = senderId
            hashMap["receiverId"] = receiverId
            hashMap["message"] = message

            firebaseDatabase.reference
                .child("Chat")
                .push().setValue(hashMap)
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

    fun uploadImageProfile(filePath: Uri): Completable {
        return Completable.create { emitter ->
            filePath?.let {
                firebaseStorage.reference.child(fileName).putFile(filePath)
                    .addOnSuccessListener {
                        // update image profile user
                        val hashMap: HashMap<String, String> = HashMap()
                        hashMap["linkImage"] = filePath.toString()
                        firebaseDatabase.reference
                            .child("User")
                            .child(getCurrentUserId())
                            .updateChildren(hashMap as Map<String, String>)
                            .addOnCompleteListener {
                                emitter.onComplete()
                            }
                            .addOnFailureListener {
                                emitter.onError(it)
                            }

                    }
            }
        }
    }

}