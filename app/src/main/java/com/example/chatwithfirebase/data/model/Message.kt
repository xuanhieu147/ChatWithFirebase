package com.example.chatwithfirebase.data.model

data class Message(
    var senderId: String = "",
    var receiverId: String = "",
    var message: String = "",
    var avatarSender: String = "",
    var imageUpload: String = "",
    var date: String = "",
    var time: String = ""
)