package com.example.chatwithfirebase.data.repository

import com.example.chatwithfirebase.data.model.NotificationData
import com.example.chatwithfirebase.data.model.PushNotification
import com.example.chatwithfirebase.data.remote.ApiFirebaseNotification
import io.reactivex.Observable
import javax.inject.Inject


class FirebaseNotificationRepositoryImp @Inject constructor(
    private val apiFirebaseNotification:ApiFirebaseNotification
    ) :FirebaseNotificationRepository{

    override fun sendNotification(pushNotification: PushNotification) : Observable<NotificationData>{
        return apiFirebaseNotification.sendNotification(pushNotification)
    }

}