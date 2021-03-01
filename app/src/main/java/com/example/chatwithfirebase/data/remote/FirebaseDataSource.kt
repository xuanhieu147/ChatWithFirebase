package com.example.chatwithfirebase.data.remote

import android.net.Uri
import android.util.Log.e
import com.example.chatwithfirebase.data.model.Message
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.utils.DateUtils
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
import javax.inject.Inject
import io.reactivex.Observable
import java.util.*
import kotlin.collections.HashMap


class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseStorage: FirebaseStorage) {

    private val userList = ArrayList<User>()
    private val messageList = ArrayList<Message>()

    private val notValue = "Not Value"
    private val fileName = "Image" + UUID.randomUUID().toString()


    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun getCurrentUserId(): String {
        if (firebaseAuth.currentUser!!.uid != null) {
            return firebaseAuth.currentUser!!.uid
        }
        return notValue
    }

    fun getInfoReceiver(userId: String): Observable<User> {
        return Observable.create { emitter ->
            firebaseDatabase.reference.child("User").child(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        user?.let { emitter.onNext(it) }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }
                })
        }
    }

    fun getInfoUser(): Observable<User> {
        return Observable.create { emitter ->
            firebaseDatabase.reference.child("User").child(getCurrentUserId())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        user?.let { emitter.onNext(it) }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }
                })
        }
    }

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

    fun searchForUser(str: String): Observable<ArrayList<User>> {
        return Observable.create { emitter ->
            firebaseDatabase.reference
                .child("User").orderByChild("fullName")
                .startAt(str)
                .endAt(str + "\uf8ff")
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

    // Get all message from user and recipients
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
                                    message.senderId == receiverId && message.receiverId == senderId
                                ) {
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

    fun sendMessage(receiverId: String, message: String, avatarSender: String): Completable {
        return Completable.create { emitter ->
            val hashMap: HashMap<String, String> = HashMap()
            hashMap["senderId"] = getCurrentUserId()
            hashMap["receiverId"] = receiverId
            hashMap["message"] = message
            hashMap["avatarSender"] = avatarSender
            hashMap["imageUpload"] = ""
            hashMap["date"] = DateUtils.getCurrentDate()!!
            hashMap["time"] = DateUtils.getCurrentTime()!!

            firebaseDatabase.reference
                .child("Message")
                .push()
                .setValue(hashMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    }
                }
                .addOnFailureListener { emitter.onError(it) }
        }
    }

    fun sendImageMessage(fileUri: Uri, receiverId: String, avatarSender: String): Completable {
        return Completable.create { emitter ->
            // Upload image
            val uploadTask: StorageTask<*>
            val storageReference = firebaseStorage.reference.child("Message Image")
            val filePath = storageReference.child(fileName)
            uploadTask = filePath.putFile(fileUri)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation filePath.downloadUrl

            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["senderId"] = getCurrentUserId()
                    hashMap["receiverId"] = receiverId
                    hashMap["message"] = "Send you an image"
                    hashMap["avatarSender"] = avatarSender
                    hashMap["imageUpload"] = url
                    hashMap["date"] = DateUtils.getCurrentDate()!!
                    hashMap["time"] = DateUtils.getCurrentTime()!!
                    // Add message
                    firebaseDatabase.reference
                        .child("Message")
                        .push()
                        .setValue(hashMap)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                emitter.onComplete()
                            }
                        }
                        .addOnFailureListener { emitter.onError(it) }
                }
            }
                .addOnFailureListener { emitter.onError(it) }
        }
    }

    fun uploadImageProfile(fileUri: Uri): Completable {
        return Completable.create { emitter ->
            val uploadTask: StorageTask<*>
            val storageReference = firebaseStorage.reference.child("User Image")
            val filePath = storageReference.child(fileName)
            uploadTask = filePath.putFile(fileUri)
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation filePath.downloadUrl

            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["avatarUser"] = url
                    // update image profile user
                    firebaseDatabase.reference
                        .child("User")
                        .child(getCurrentUserId())
                        .updateChildren(hashMap as Map<String, String>)
                        .addOnCompleteListener { emitter.onComplete() }
                        .addOnFailureListener { emitter.onError(it) }
                }
            }
                .addOnFailureListener { emitter.onError(it) }
        }
    }

    fun updateFullName(fullName: String): Completable {
        return Completable.create { emitter ->
            val hashMap: HashMap<String, String> = HashMap()
            hashMap["fullName"] = fullName
            firebaseDatabase.reference
                .child("User")
                .child(getCurrentUserId())
                .updateChildren(hashMap as Map<String, String>)
                .addOnCompleteListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(it) }
        }
    }

    fun updateStatusUser(status: String): Completable {
        return Completable.create { emitter ->
            val hashMap: HashMap<String, String> = HashMap()
            hashMap["status"] = status
            firebaseDatabase.reference
                .child("User")
                .child(getCurrentUserId())
                .updateChildren(hashMap as Map<String, String>)
                .addOnCompleteListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(it) }
        }
    }
}