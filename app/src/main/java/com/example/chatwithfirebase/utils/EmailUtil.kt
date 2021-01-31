package com.example.chatwithfirebase.utils

import java.util.regex.Pattern

/**
 * Copy by Duc Minh
 */

object EmailUtil {
    private val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    fun checkValidEmail(email: String?): Boolean {
        return email != null && email.isNotEmpty() && EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }
}