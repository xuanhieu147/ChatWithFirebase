package com.example.chatwithfirebase.utils

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.example.chatwithfirebase.R
import www.sanju.motiontoast.MotionToast

/**
 * Created by Duc Minh
 */

object ToastUtils {

    fun toastSuccess(context: Context?, title: Any?, message: Any?) {
        context?.let { it ->
            val tit: String = when (title) {
                is Int -> it.getString(title)
                is String -> title
                else -> ""
            }

            val mes: String = when (message) {
                is Int -> it.getString(message)
                is String -> message
                else -> ""
            }

            MotionToast.createColorToast(
                context as Activity,
                tit,
                mes,
                MotionToast.TOAST_SUCCESS,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION ,
                ResourcesCompat.getFont(context, R.font.louis_george_cafe)
            )
        }
    }

    fun toastError(context: Context?, title: Any?, message: Any?) {
        context?.let { it ->
            val tit: String = when (title) {
                is Int -> it.getString(title)
                is String -> title
                else -> ""
            }

            val mes: String = when (message) {
                is Int -> it.getString(message)
                is String -> message
                else -> ""
            }

            MotionToast.createColorToast(
                context as Activity,
                tit,
                mes,
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION ,
               ResourcesCompat.getFont(context, R.font.louis_george_cafe)
            )
        }
    }

    fun toastWarning(context: Context?, title: Any?, message: Any?) {
        context?.let { it ->
            val tit: String = when (title) {
                is Int -> it.getString(title)
                is String -> title
                else -> ""
            }

            val mes: String = when (message) {
                is Int -> it.getString(message)
                is String -> message
                else -> ""
            }

            MotionToast.createColorToast(
                context as Activity,
                tit,
                mes,
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION ,
               ResourcesCompat.getFont(context, R.font.louis_george_cafe)
            )
        }
    }

    fun toastInfo(context: Context?, title: Any?, message: Any?) {
        context?.let { it ->
            val tit: String = when (title) {
                is Int -> it.getString(title)
                is String -> title
                else -> ""
            }

            val mes: String = when (message) {
                is Int -> it.getString(message)
                is String -> message
                else -> ""
            }

            MotionToast.createColorToast(
                context as Activity,
                tit,
                mes,
                MotionToast.TOAST_INFO,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION ,
               ResourcesCompat.getFont(context, R.font.louis_george_cafe)
            )
        }
    }

    fun toastDelete(context: Context?, title: Any?, message: Any?) {
        context?.let { it ->
            val tit: String = when (title) {
                is Int -> it.getString(title)
                is String -> title
                else -> ""
            }

            val mes: String = when (message) {
                is Int -> it.getString(message)
                is String -> message
                else -> ""
            }

            MotionToast.createColorToast(
                context as Activity,
                tit,
                mes,
                MotionToast.TOAST_DELETE,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION ,
               ResourcesCompat.getFont(context, R.font.louis_george_cafe)
            )
        }
    }

}