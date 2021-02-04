package com.example.chatwithfirebase.base.constants

/**
 * Created by Duc Minh
 */

object Constants {
    // Firebase
    const val FIREBASE_DATABASE_URL = "https://chatwithfirebase-ee489-default-rtdb.firebaseio.com"
    const val AVATAR_DEFAULT_USER =
        "https://firebasestorage.googleapis.com/v0/b/chatwithfirebase-ee489.appspot.com/o/account.png?alt=media&token=36716229-9aef-4190-bac9-057a3495f3ba"

    // SharedPreferencesManager
    const val FILE_NAME = "CHAT_WITH_FIREBASE"
    const val FIREBASE_USER_UID = "FIREBASE_USER_UID"
    const val EMAIL = "EMAIL"
    const val FULL_NAME = "FULL_NAME"
    const val URL_AVATAR = "URL_AVATAR"
    const val TOKEN = "TOKEN"

    // Notification
    const val BASE_URL = "https://fcm.googleapis.com"
    const val CONTENT_TYPE = "application/json"
    const val SERVER_KEY =
        "AAAAf8bTlOY:APA91bH7eLsVmCjRuLCAJc4rlV-dolwBKnf-UiyYecqrRqQ9PcoWMy42BDp7316NO4oGr6eB8a3co4D6FSsEn16soqyHYCBhha_EGZ6Q_35sq2qbQVhUkMtQ5Kxo8hisKvdC_TBoDkI7"

    // Permission
    const val CAMERA = 101
    const val READ_EXTERNAL_STORAGE = 102
    const val WRITE_EXTERNAL_STORAGE = 103
}