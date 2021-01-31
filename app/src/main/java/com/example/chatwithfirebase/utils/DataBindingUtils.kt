package com.example.chatwithfirebase.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.constants.Constants

/**
 * Created by Duc Minh
 */

class DataBindingUtils {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, url: String?) {
            if (url.isNullOrEmpty()) {
                imageView.setImageResource(R.drawable.ic_logo_no_text)
            }
            else{
                Glide.with(imageView.context).load(url).into(imageView)
            }
        }
    }
}