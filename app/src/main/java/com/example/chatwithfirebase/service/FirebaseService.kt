package com.example.chatwithfirebase.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.manager.SharedPreferencesManager
import com.example.chatwithfirebase.ui.home.HomeActivity
import com.example.chatwithfirebase.ui.message.MessageActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Inject
import kotlin.random.Random

/** Code by Duc Minh */
class FirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager


    companion object {

        const val CHANNEL_NOTIFICATION_ID = "NOTIFICATION_CHANNEL"
        const val CHANNEL_CHAT_NAME = "CHANNEL_FIREBASE_CHAT"
    }

    /*
    - Khi một thiết bị cài đặt ứng dụng thì nó sẽ sinh ra một device_token trong method onNewToken() và đăng kí nó với Firebase
    - Firebase sẽ dựa vào các token này để gửi thông báo đến các thiết bị
     */

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        if(this::sharedPreferencesManager.isInitialized){
            sharedPreferencesManager.saveToken(newToken)
        }
    }

    /** Json notification example */
//    {
//        "message":{
//        "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
//        "data":{
//        "title":"Portugal vs. Denmark",
//        "body":"great match!"
//    }
//    }
//    }

    // handling received messages - data messages - forebackground, background
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // handling event when click notification
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // handling notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_NOTIFICATION_ID)
            .setContentTitle(remoteMessage.data["title"])
            .setContentText(remoteMessage.data["message"])
            .setSmallIcon(R.drawable.ic_logo_no_text)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        notificationManager.notify(notificationId, notification)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(CHANNEL_NOTIFICATION_ID,CHANNEL_CHAT_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description="MY_FIREBASE_CHAT_DESCRIPTION"
            enableLights(true)
            lightColor= Color.WHITE
        }
        notificationManager.createNotificationChannel(channel)
    }
}