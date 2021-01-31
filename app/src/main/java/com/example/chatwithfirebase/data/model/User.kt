package com.example.chatwithfirebase.data.model

data class User(
    var userId: String = "",
    var email: String = "",
    var password: String = "",
    var avatarUser: String = "",
    var fullName: String = "",
    var online: String = "",
    var lastMessage: String = "",
    var date: String = "",
    var time: String = ""
)