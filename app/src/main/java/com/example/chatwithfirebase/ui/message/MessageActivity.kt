package com.example.chatwithfirebase.ui.message

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityGradient
import com.example.chatwithfirebase.base.constants.Constants
import com.example.chatwithfirebase.data.model.NotificationData
import com.example.chatwithfirebase.data.model.PushNotification
import com.example.chatwithfirebase.databinding.ActivityMessageBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.message.adapter.MessageAdapter
import com.example.chatwithfirebase.utils.ToastUtils
import javax.inject.Inject


class MessageActivity : BaseActivityGradient<ActivityMessageBinding, MessageViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var messageAdapter: MessageAdapter

    private lateinit var messageViewModel: MessageViewModel

    override fun getViewModel(): MessageViewModel {
        messageViewModel = ViewModelProvider(this, factory).get(MessageViewModel::class.java)
        return messageViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_message

    override fun getBindingVariable(): Int = BR.messageViewModel

    override fun updateUI(savedInstanceState: Bundle?) {

        binding.imgBack.setOnClickListener { onBackPressed() }

        // get info Receiver
        messageViewModel.getInfoReceiver(getIdReceiver()!!)
        messageViewModel.liveDataGetInfoReceiver().observe(this, {
            if (it != null) {
                binding.user = it
                setStatusColor()
            } else {
                toast(resources.getString(R.string.error_get_data))
            }
        })

        // get all message
        binding.rclChat.apply {
            setHasFixedSize(true)
            adapter = messageAdapter
        }

        messageViewModel.getAllMessage(getIdReceiver()!!)
        messageViewModel.liveDataGetAllMessage().observe(this, {
            if (it != null) {
                messageAdapter.clearItems()
                messageAdapter.addItems(it)
                // scroll last position
                binding.rclChat.scrollToPosition(it.size - 1)
            } else {
                toast(resources.getString(R.string.error_get_data))
            }
        })

        // send message
        binding.imgSend.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            if (message.isNullOrEmpty()) {
                ToastUtils.toastError(this, R.string.error, R.string.type_message)
            } else {
                messageViewModel.sendMessage(
                    getIdReceiver()!!,
                    message,
                    messageViewModel.getUrlAvatar()
                )
                // clear text
                binding.edtMessage.text?.clear()
                scrollLastPosition()
                pushNotification(message)
            }
        }

        //send image message
        binding.imgLibrary.setOnClickListener {
            checkPermission(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "WRITE_EXTERNAL_STORAGE",
                Constants.WRITE_EXTERNAL_STORAGE
            )
            checkPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                "READ_EXTERNAL_STORAGE",
                Constants.READ_EXTERNAL_STORAGE
            )
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(
                Intent.createChooser(intent, R.string.pick_image.toString()),
                438
            )
        }

        // camera
        binding.imgCamera.setOnClickListener {
            checkPermission(android.Manifest.permission.CAMERA, "Camera", Constants.CAMERA)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(
                Intent.createChooser(intent, R.string.pick_image.toString()),
                438
            )
        }
    }

    private fun setStatusColor() {
        if (binding.tvStatus.text.toString() == "online") {
            binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.blue_61))
        } else {
            binding.tvStatus.setTextColor(ContextCompat.getColor(this, R.color.black))
        }
    }

    private fun scrollLastPosition() {
        // auto scroll last position when the layout size changes
        binding.rclChat.apply {
            addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                scrollToPosition(messageAdapter.itemCount - 1)
            }
        }
    }

    private fun pushNotification(message: String) {
        var topic = "/topics/${getIdReceiver()}"
        PushNotification(
            NotificationData(
                messageViewModel.getCurrentUserId(),
                messageViewModel.getFullName(), message
            ), topic
        ).also {
            messageViewModel.sendNotification(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 438 && resultCode == RESULT_OK && data != null && data!!.data != null) {
            val fileUri = data.data
            messageViewModel.sendImageMessage(
                fileUri!!,
                getIdReceiver()!!,
                messageViewModel.getUrlAvatar()
            )
        }
    }
}