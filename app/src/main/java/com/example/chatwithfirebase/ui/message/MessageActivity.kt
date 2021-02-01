package com.example.chatwithfirebase.ui.message

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log.e
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityGradient
import com.example.chatwithfirebase.databinding.ActivityMessageBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.message.adapter.MessageAdapter
import com.example.chatwithfirebase.utils.DateUtils
import com.example.chatwithfirebase.utils.LogUtil
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import javax.inject.Inject


class MessageActivity : BaseActivityGradient<ActivityMessageBinding, MessageViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var messageViewModel: MessageViewModel

    @Inject
    lateinit var messageAdapter: MessageAdapter

    override fun getViewModel(): MessageViewModel {
        messageViewModel = ViewModelProvider(this, factory).get(MessageViewModel::class.java)
        return messageViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_message

    override fun getBindingVariable(): Int = BR.messageViewModel

    override fun updateUI(savedInstanceState: Bundle?) {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        // get info Receiver
        messageViewModel.getInfoReceiver(getIdReceiver()!!)
        messageViewModel.liveDataGetInfoReceiver().observe(this, {
            if (it != null) {
                binding.user = it
            } else {
                toast(resources.getString(R.string.error_get_data))
            }
        })

        // get all message
        binding.rvChat.apply {
            setHasFixedSize(true)
            adapter = messageAdapter
        }

        messageViewModel.getAllMessage(getIdReceiver()!!)
        messageViewModel.liveDataGetAllMessage().observe(this, {
            if (it != null) {
                messageAdapter.clearItems()
                messageAdapter.addItems(it)

                // scroll last position
                binding.rvChat.scrollToPosition(it.size - 1)

                // update last message and time
                messageViewModel.updateLastMessageAndTime(
                    getIdReceiver()!!,
                    it[it.size - 1].message,
                    it[it.size - 1].date,
                    it[it.size - 1].time)

            } else {
                toast(resources.getString(R.string.error_get_data))
            }
        })

        // send message
        binding.imgSend.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            messageViewModel.sendMessage(getIdReceiver()!!, message,getAvatarSender()!!,"")

            // clear text
            binding.edtMessage.text?.clear()

            // auto scroll last position when the layout size changes
            binding.rvChat.apply{
                addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                scrollToPosition(messageAdapter.itemCount - 1)
            }
            }
        }

        //send imageMessage
        binding.imgCamera.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Pick Image"), 438)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 438 && resultCode == RESULT_OK && data != null && data!!.data != null) {
            showLoadingDialog()
            val fileUri = data.data
            val storageReference = FirebaseStorage.getInstance().reference.child("Chats Image")
            val ref = FirebaseDatabase.getInstance().reference
            val messageId = ref.push().key
            val filePath = storageReference.child("$messageId.jpg")

            val uploadTask: StorageTask<*>
            uploadTask = filePath.putFile(fileUri!!)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation filePath.downloadUrl

            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()
                    messageViewModel.sendImgaeMessage(getIdReceiver()!!,getAvatarSender()!!,url)
                    hideLoadingDialog()
                }
            }
        }
    }
}