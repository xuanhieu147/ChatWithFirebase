package com.example.chatwithfirebase.data.remote

import android.net.Uri
import com.example.chatwithfirebase.data.model.Message
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.utils.DateUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import javax.inject.Inject
import io.reactivex.Observable
import java.util.*
import kotlin.collections.HashMap

/**
 * Code by Duc Minh
 */

class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage) {

    private val userList = ArrayList<User>()
    private val messageList = ArrayList<Message>()

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

    fun getInfoReceiver(userId:String):Observable<User>{
        return Observable.create { emitter ->
            firebaseDatabase.reference.child("User").child(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        user?.let {
                            emitter.onNext(it)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                })

        }
    }

    fun getInfoUser():Observable<User>{
        return Observable.create { emitter ->
            firebaseDatabase.reference.child("User").child(getCurrentUserId())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        user?.let {
                            emitter.onNext(it)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                })

        }
    }

    // get all user
    fun getAllUser(): Observable<ArrayList<User>> {
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
    fun getAllMessage(receiverId: String): Observable<ArrayList<Message>> {
        return Observable.create { emitter ->
            firebaseDatabase.reference.child("Message")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        messageList.clear()
                        for (dataSnapshot: DataSnapshot in snapshot.children) {
                            val message = dataSnapshot.getValue(Message::class.java)
                            message?.let {
                                val senderId = getCurrentUserId()
                                if (message.senderId == senderId && message.receiverId == receiverId ||
                                    message.senderId == receiverId && message.receiverId == senderId) {

                                    messageList.add(it)
                                    emitter.onNext(messageList)
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

    fun sendMessage(receiverId: String, message: String,avatarSender:String,imageUpload:String): Completable {
        return Completable.create { emitter ->
            // add chat message

            if(imageUpload.isNullOrEmpty()) {
                val hashMap: HashMap<Any, Any> = HashMap()
                hashMap["senderId"] = getCurrentUserId()
                hashMap["receiverId"] = receiverId
                hashMap["message"] = message
                hashMap["avatarSender"] = avatarSender
                hashMap["imageUpload"] = ""
                hashMap["date"] = DateUtils.getCurrentDate()!!
                hashMap["time"] = DateUtils.getCurrentTime()!!

                firebaseDatabase.reference
                    .child("Message")
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

            else {
                val hashMap: HashMap<Any, Any> = HashMap()
                hashMap["senderId"] = getCurrentUserId()
                hashMap["receiverId"] = receiverId
                hashMap["message"] = ""
                hashMap["avatarSender"] = avatarSender
                hashMap["date"] = DateUtils.getCurrentDate()!!
                hashMap["time"] = DateUtils.getCurrentTime()!!

                firebaseDatabase.reference
                    .child("Message")
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
    }

    fun uploadImageProfile(filePath: Uri): Completable {
        return Completable.create { emitter ->
            filePath?.let {
                firebaseStorage.reference.child(fileName).putFile(filePath)
                    .addOnSuccessListener {
                        // update image profile user
                        val hashMap: HashMap<String, String> = HashMap()
                        hashMap["avatarSender"] = filePath.toString()
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

    fun updateLastMessageAndTime(userId:String,lastMessage:String,date:String,time:String): Completable {
        return Completable.create{emitter->
            val hashMap: HashMap<String, String> = HashMap()
            hashMap["lastMessage"] = lastMessage
            hashMap["date"] = date
            hashMap["time"] = time
            firebaseDatabase.reference.child("User").child(userId)
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