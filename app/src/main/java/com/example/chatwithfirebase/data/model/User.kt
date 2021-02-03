package com.example.chatwithfirebase.data.model

data class User(
    var userId: String = "",
    var email: String = "",
    var password: String = "",
    var avatarUser: String = "",
    var fullName: String = "",
    var status: String = "",
)