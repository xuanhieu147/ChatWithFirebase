package com.example.chatwithfirebase.base.manager

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.chatwithfirebase.base.constants.Constants

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            Constants.FILE_NAME, Activity.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor

    init {
        @SuppressLint("CommitPrefEdits")
        editor = sharedPreferences.edit()
    }

    private fun getString(key: String?, defaultValue: String): String {
        val value = sharedPreferences.getString(key, defaultValue)
        if (value != null) {
            return value
        }
        return defaultValue
    }

    private fun getInt(key: String?, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    private fun putConfig(key: String?, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    private fun putConfig(key: String?, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }


    private fun removeConfig(key: String) {
        editor.remove(key).apply()
    }

    fun saveUser(firebaseUserUId: String?) {
        putConfig(Constants.FIREBASE_USER_UID, firebaseUserUId)
    }

    fun getUser() : String = getString(Constants.FIREBASE_USER_UID, "")


    fun removeUser() {
        removeConfig(Constants.FIREBASE_USER_UID)
    }

}