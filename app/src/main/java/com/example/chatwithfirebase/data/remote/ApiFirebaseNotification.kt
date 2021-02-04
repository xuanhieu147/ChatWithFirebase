package com.example.chatwithfirebase.data.remote

import com.example.chatwithfirebase.base.constants.Constants.CONTENT_TYPE
import com.example.chatwithfirebase.base.constants.Constants.SERVER_KEY
import com.example.chatwithfirebase.data.model.NotificationData
import com.example.chatwithfirebase.data.model.PushNotification
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiFirebaseNotification {

    @Headers("Authorization: key=$SERVER_KEY", "Content-type:$CONTENT_TYPE")
    @POST("fcm/send")
    fun sendNotification(@Body pushNotification: PushNotification): Observable<NotificationData>
}