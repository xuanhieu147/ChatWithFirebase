package com.example.chatwithfirebase.data.model

data class User(
    var userId: String = "",
    var email: String = "",
    var password: String = "",
    var linkImage: String = "",
    var fullName: String = "",
    var online: String = ""
)