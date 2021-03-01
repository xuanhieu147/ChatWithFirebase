package com.example.chatwithfirebase.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*



object DateUtils {

    fun getCurrentDate(): String? {
        var currentDate: String?
        currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            current.format(formatter)

        } else {
            var date = Date()
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            formatter.format(date)
        }
        return currentDate
    }

    fun getCurrentTime(): String? {
        var currentTime: String?
        currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("hh:mm a")
            current.format(formatter)

        } else {
            var date = Date()
            val formatter = SimpleDateFormat("hh:mm a")
            formatter.format(date)
        }
        return currentTime
    }
}