package com.example.chatwithfirebase.ui.message

import android.util.Log.e
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.data.model.Message
import com.example.chatwithfirebase.data.model.NotificationData
import com.example.chatwithfirebase.data.model.PushNotification
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.utils.LogUtil
import javax.inject.Inject

class MessageViewModel @Inject constructor() : BaseViewModel() {

    private val liveDataUser = MutableLiveData<User>()
    private val liveDataListMessage = MutableLiveData<ArrayList<Message>>()

    // Get Info Receiver
    fun liveDataGetInfoReceiver(): MutableLiveData<User> = liveDataUser

    fun getInfoReceiver(userId: String) {
        compositeDisposable.add(
            firebaseDataRepository.getInfoReceiver(userId)
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getInfoReceiverSuccess, this::getInfoReceiverError)
        )
    }

    private fun getInfoReceiverSuccess(user: User) {
        liveDataUser.value = user
    }

    private fun getInfoReceiverError(t: Throwable) {
        liveDataUser.value = null
    }

    // get All Message
    fun liveDataGetAllMessage(): MutableLiveData<ArrayList<Message>> = liveDataListMessage

    fun getAllMessage(receiverId: String) {
        compositeDisposable.add(
            firebaseDataRepository.getAllMessage(receiverId)
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getAllMessageSuccess, this::getAllMessageError)
        )
    }

    private fun getAllMessageSuccess(messageList: ArrayList<Message>) {
        liveDataListMessage.value = messageList
    }

    private fun getAllMessageError(t: Throwable) {
        liveDataListMessage.value = null
    }

    // send message
    fun sendMessage(receiverId: String, message: String, avatarSender: String, ) {
        compositeDisposable.add(
            firebaseDataRepository.sendMessage(receiverId, message, avatarSender)
                .compose(schedulerProvider.ioToMainCompletableScheduler())
                .subscribe(this::sendMessageSuccess, this::sendMessageError)
        )
    }

    // send ImageMessage
    fun sendImageMessage(fileUri: Uri, receiverId: String, avatarSender: String) {
        setLoading(true)
        compositeDisposable.add(
            firebaseDataRepository.sendImageMessage(fileUri, receiverId, avatarSender)
                .compose(schedulerProvider.ioToMainCompletableScheduler())
                .subscribe(this::sendMessageSuccess, this::sendMessageError)
        )
    }

    private fun sendMessageSuccess() {
        setLoading(false)
        LogUtil.error("Send Message Success")
    }

    private fun sendMessageError(t: Throwable) {
        LogUtil.error("Send Message Error $t")
    }


    // Send Notification
    fun sendNotification(pushNotification: PushNotification){
        compositeDisposable.add(
            firebaseNotificationRepository.sendNotification(pushNotification)
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::sendNotificationSuccess,this::sendNotificationError)
        )
    }

    private fun sendNotificationSuccess(notificationData: NotificationData){
        LogUtil.error(" Push Notification Success")
    }

    private fun sendNotificationError(t:Throwable) {
        LogUtil.error(" Push Notification Error $t")
    }

    fun getCurrentUserId(): String{
        return firebaseDataRepository.getCurrentUserId()
    }

    fun getUrlAvatar():String{
        return sharedPreferencesManager.getUrlAvatar()
    }

    fun getFullName():String{
        return sharedPreferencesManager.getFullName()
    }
}