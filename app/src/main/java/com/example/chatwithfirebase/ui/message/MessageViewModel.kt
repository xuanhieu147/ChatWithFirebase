package com.example.chatwithfirebase.ui.message

import androidx.lifecycle.MutableLiveData
import com.example.chatwithfirebase.base.BaseViewModel
import com.example.chatwithfirebase.data.model.Message
import com.example.chatwithfirebase.data.model.User
import com.example.chatwithfirebase.utils.LogUtil
import javax.inject.Inject

class MessageViewModel @Inject constructor() : BaseViewModel() {

    private val liveDataUser = MutableLiveData<User>()
    private val liveDataListMessage = MutableLiveData<ArrayList<Message>>()

    // Get Info Receiver
    fun liveDataGetInfoReceiver() : MutableLiveData<User> = liveDataUser

    fun getInfoReceiver(userId: String) {
        compositeDisposable.add(
            firebaseDataRepository.getInfoReceiver(userId)
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getInfoReceiverSuccess, this::getInfoReceiverError)
        )
    }

    private fun getInfoReceiverSuccess(user: User) { liveDataUser.value = user }
    private fun getInfoReceiverError(t: Throwable) { liveDataUser.value = null }

    // get All Message
    fun liveDataGetAllMessage() : MutableLiveData<ArrayList<Message>> = liveDataListMessage

    fun getAllMessage(receiverId: String){
        compositeDisposable.add(
            firebaseDataRepository.getAllMessage(receiverId)
                .compose(schedulerProvider.ioToMainObservableScheduler())
                .subscribe(this::getAllMessageSuccess,this::getAllMessageError)
        )
    }

    private fun getAllMessageSuccess(messageList:ArrayList<Message>) { liveDataListMessage.value = messageList }
    private fun getAllMessageError(t:Throwable) { liveDataListMessage.value = null }

    // send message
    fun sendMessage(receiverId: String, message: String,avatarSender:String,imageUpload:String){
        compositeDisposable.add(
            firebaseDataRepository.sendMessage(receiverId, message,avatarSender,imageUpload)
                .compose(schedulerProvider.ioToMainCompletableScheduler())
                .subscribe(this::sendMessageSuccess,this::sendMessageError)
        )
    }

    private fun sendMessageSuccess() { LogUtil.error("Success") }
    private fun sendMessageError(t:Throwable) { LogUtil.error(t.toString()) }

    // update last message and time
    fun updateLastMessageAndTime(userId:String,lastMessage:String,date:String,time:String){
        compositeDisposable.add(
            firebaseDataRepository.updateLastMessageAndTime(userId,lastMessage,date,time)
                .compose(schedulerProvider.ioToMainCompletableScheduler())
                .subscribe(this::updateLastMessageAndTimeSuccess,this::updateLastMessageAndTimeError)
        )
    }

    private fun updateLastMessageAndTimeSuccess(){ LogUtil.error("Success") }
    private fun updateLastMessageAndTimeError(t:Throwable){ LogUtil.error(t.toString()) }


}