package com.example.chatwithfirebase.ui.setting

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.BaseActivityGradient
import com.example.chatwithfirebase.base.constants.Constants
import com.example.chatwithfirebase.databinding.ActivitySettingBinding
import com.example.chatwithfirebase.databinding.LayoutDialogEditBinding
import com.example.chatwithfirebase.databinding.LayoutUpdateAvatarBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.login.LoginActivity
import com.example.chatwithfirebase.ui.setting.notification.NotificationActivity
import com.example.chatwithfirebase.utils.ToastUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import javax.inject.Inject


class SettingActivity : BaseActivityGradient<ActivitySettingBinding, SettingViewModel>() {

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

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        settingViewModel.getInfoUser()
        settingViewModel.getUser().observe(this, {
            it?.let { binding.user = it }
        })

        binding.rlNotification.setOnClickListener {
            openNotificationScreen()
        }

        binding.rlLogout.setOnClickListener {
            showActionDialog(
                getString(R.string.logout),
                getString(R.string.question_log_out),
                getString(R.string.ok)
            ) { _, _ ->
                settingViewModel.signOut()
                openLoginScreen()
            }
        }

        updateAvatar()
        updateFullName()
    }

    private fun openLoginScreen() {
        goScreen(
            LoginActivity::class.java,
            true,
            R.anim.slide_out_left,
            R.anim.slide_in_right
        )
    }

    private fun openNotificationScreen() {
        goScreen(
            NotificationActivity::class.java,
            false,
            R.anim.slide_out_left,
            R.anim.slide_in_right
        )
    }


    private fun openCamera() {
        checkPermission(android.Manifest.permission.CAMERA, "Camera", Constants.CAMERA)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(
            Intent.createChooser(intent, R.string.pick_image.toString()),
            438
        )
    }

    private fun openPhotoLibrary() {
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
        if (isCheckPermission == true) {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Pick Image"), 438)
        }

    }

    private fun updateAvatar() {
        binding.imgAvatar.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(this@SettingActivity, R.style.SheetDialog)
            val bindingBottomSheetDialog: LayoutUpdateAvatarBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.layout_update_avatar,
                null,
                false
            )
            bottomSheetDialog.setContentView(bindingBottomSheetDialog.root)
            bindingBottomSheetDialog.btnTakePhoto.setOnClickListener {
                openCamera()
            }
            bindingBottomSheetDialog.btnSelectPicture.setOnClickListener {
                openPhotoLibrary()
            }
            bottomSheetDialog.show()


        }
    }

    private fun updateFullName() {
        binding.imgEdit.setOnClickListener {

            val dialog = Dialog(this, R.style.SheetDialog)
            val bindingDialog: LayoutDialogEditBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this),
                R.layout.layout_dialog_edit,
                null,
                false
            )

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(bindingDialog.root)

            bindingDialog.tvTitle.text = title.toString()
            bindingDialog.edtFullName.hint = binding.tvFullName.text

            bindingDialog.btnOk.setOnClickListener {
                if (bindingDialog.edtFullName.text.toString() == "") {
                    ToastUtils.toastError(this, R.string.full_name, R.string.type_full_name)
                } else {
                    settingViewModel.updateFullName(bindingDialog.edtFullName.text.toString())
                    dialog.dismiss()
                }
            }

            bindingDialog.btnCancel.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }

//        settingViewModel.uiEventLiveData.observe(this, {
//            if (it == SettingViewModel.SAME_FULL_NAME) {
//                ToastUtils.toastError(this, R.string.full_name, R.string.same_full_name)
//            }
//        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 438 && resultCode == RESULT_OK && data != null && data!!.data != null) {
            val fileUri = data.data
            settingViewModel.updateAvatar(fileUri!!)
        }
    }

}