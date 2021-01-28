package com.example.chatwithfirebase.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class DataBindingUtils {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, url: String) {
            if (!url.isNullOrEmpty()) {
                Glide.with(imageView.context).load(url).into(imageView)
            }
        }
    }
}