package com.example.chatwithfirebase.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Copy by Duc Minh
 */

object KeyboardUtil {
    fun hideSoftKeyBoard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText && activity.currentFocus != null) {
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        }
    }

    fun showSoftKeyBoard(view: View) {
        view.post {
            val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}