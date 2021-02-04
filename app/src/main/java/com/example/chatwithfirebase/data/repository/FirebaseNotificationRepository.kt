package com.example.chatwithfirebase.data.repository

import com.example.chatwithfirebase.data.model.NotificationData
import com.example.chatwithfirebase.data.model.PushNotification
import io.reactivex.Observable

interface FirebaseNotificationRepository {

    fun sendNotification(pushNotification: PushNotification): Observable<NotificationData>
}