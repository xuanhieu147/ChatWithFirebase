package com.example.chatwithfirebase.ui.setting

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivity
import com.example.chatwithfirebase.databinding.ActivitySettingBinding
import com.example.chatwithfirebase.databinding.CustomDialogEditBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.setting.notification.NotificationActivity
import com.example.chatwithfirebase.utils.ToastUtils
import javax.inject.Inject


class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var settingViewModel: SettingViewModel

    override fun getViewModel(): SettingViewModel {
        settingViewModel = ViewModelProvider(this, factory).get(SettingViewModel::class.java)
        return settingViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_setting

    override fun getBindingVariable(): Int = BR.settingViewModel

    override fun updateUI(savedInstanceState: Bundle?) {

        // get info user
        settingViewModel.getInfoUser()
        settingViewModel.getUser().observe(this, {
            it?.let {
                binding.user = it
            }
        })

        binding.rlNotifications.setOnClickListener {
            goScreen(
                NotificationActivity::class.java,
                false, R.anim.slide_in_right, R.anim.slide_out_left
            )
        }

        binding.rlLogOut.setOnClickListener {

        }

        //update Avatar
        binding.imgAvatar.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Pick Image"), 438)
        }

        //update FullName
        updateFullName()

        // camera
        binding.imgCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(Intent.createChooser(intent, R.string.title_img.toString()), 438)
        }
    }

    //update FullName
    private fun updateFullName() {
        binding.imgEdit.setOnClickListener {

            val dialog = Dialog(this)
            val bindingDialog: CustomDialogEditBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.custom_dialog_edit,
                null,
                false
            )

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(bindingDialog.root)

            bindingDialog.tvTitle.text = title.toString()

            bindingDialog.edFullName.hint = binding.tvFullName.text

            bindingDialog.btnOk.setOnClickListener {
                if (bindingDialog.edFullName.text.toString() == "") {
                    ToastUtils.toastError(this, R.string.full_name, R.string.type_full_name)
                } else {
                    settingViewModel.updateFullName(bindingDialog.edFullName.text.toString())
                    dialog.dismiss()
                }
            }

            bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }
            val window: Window? = dialog.window
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.show()
        }

        settingViewModel.uiEventLiveData.observe(this, {
            if (it == SettingViewModel.SAME_FULL_NAME) {
                ToastUtils.toastError(this, R.string.full_name, R.string.same_full_name)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 438 && resultCode == RESULT_OK && data != null && data!!.data != null) {
            val fileUri = data.data
            settingViewModel.updateAvatar(fileUri!!)
        }
    }

}