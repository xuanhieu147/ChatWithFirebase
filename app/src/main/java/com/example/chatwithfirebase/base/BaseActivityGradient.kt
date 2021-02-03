package com.example.chatwithfirebase.base

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.utils.ToastUtils
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity


/**
 * Custom by Duc Minh
 * Status bar text color white and background gradient :v
 */
abstract class BaseActivityGradient<T : ViewDataBinding, V : BaseViewModel> :
    DaggerAppCompatActivity() {

    lateinit var binding: T
    lateinit var loading: AlertDialog
    private var isCancelable = false
    val CAMERA = 101
    val READ_EXTERNAL_STORAGE = 102
    val WRITE_EXTERNAL_STORAGE = 103

    protected abstract fun getViewModel(): V

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    @LayoutRes
    open fun getLayoutIdLoading(): Int = -1

    open fun getThemResId(): Int = R.style.CustomDialog

    protected abstract fun getBindingVariable(): Int

    protected abstract fun updateUI(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
        statusBarBackgroundGradient()
        performDataBinding()
        updateUI(savedInstanceState)
        initDialog()
        getViewModel().onStart()
        loadingViewModel()
    }


    override fun onDestroy() {
        getViewModel().onStop()
        super.onDestroy()
    }

    private fun performDI() {
        AndroidInjection.inject(this)
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setVariable(getBindingVariable(), getViewModel())
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    fun doFinishActivity(vararg anim: Int) {
        finish()
        if (anim.isNotEmpty()) {
            overridePendingTransition(0, anim[0])
        } else {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    @Throws
    open fun openFragment(
        resId: Int,
        fragmentClazz: Class<*>,
        args: Bundle?,
        addBackStack: Boolean
    ) {
        val tag = fragmentClazz.simpleName
        try {
            val isExisted =
                supportFragmentManager.findFragmentByTag(tag) != null   // IllegalStateException
            if (!isExisted) {
                val fragment: Fragment
                try {
                    fragment =
                        (fragmentClazz as Class<Fragment>).newInstance().apply { arguments = args }
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.add(resId, fragment, tag)

                    if (addBackStack) {
                        transaction.addToBackStack(tag)
                    }
                    transaction.commitAllowingStateLoss()

                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun checkPermission(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    ToastUtils.toastSuccess(this, R.string.permission, R.string.permission_granted)
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(
                    permission,
                    name,
                    requestCode
                )

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String){
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                ToastUtils.toastError(this, R.string.permission, R.string.permission_type)
            }
            else{
                ToastUtils.toastSuccess(this, R.string.permission, R.string.permission_granted)

            }
        }

        when(requestCode){
            CAMERA -> innerCheck("CAMERA")
            READ_EXTERNAL_STORAGE -> innerCheck("READ_EXTERNAL_STORAGE")
            WRITE_EXTERNAL_STORAGE -> innerCheck("WRITE_EXTERNAL_STORAGE")

        }

    }

    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(
                    this@BaseActivityGradient,
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog = builder.create()
        dialog.show()
    }


    open fun goScreen(activityClazz: Class<*>, isFinish: Boolean, vararg aniInt: Int) {
        val intent = Intent(this, activityClazz)
        startActivity(intent)
        overridePendingTransition(aniInt[0], aniInt[1])
        if (isFinish) {
            finish()
        }
    }

    open fun goScreenAndPutString(activityClazz: Class<*>, isFinish: Boolean,idReceiver:String,vararg aniInt: Int) {
        val intent = Intent(this, activityClazz)
        intent.putExtra("idReceiver",idReceiver)
        startActivity(intent)
        overridePendingTransition(aniInt[0], aniInt[1])
        if (isFinish) {
            finish()
        }
    }

    open fun getIdReceiver() : String? {
        return intent.getStringExtra("idReceiver")
    }

    open fun launchIntent(intent: Intent, isFinish: Boolean, vararg aniInt: Int) {
        startActivity(intent)
        overridePendingTransition(aniInt[0], aniInt[1])
        if (isFinish) {
            finish()
        }
    }

    /**
     * Show toast
     * @param msg
     */
    fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    /**
     * Init dialog loading
     */
    private fun initDialog() {
        val builder: AlertDialog.Builder = if (getThemResId() != -1)
            AlertDialog.Builder(this, getThemResId()) else AlertDialog.Builder(this)


        builder.setCancelable(isCancelable)
        builder.setView(if (getLayoutIdLoading() == -1) R.layout.item_loading else getLayoutIdLoading())
        loading = builder.create()
    }


    /**
     * Show dialog loading
     */
    open fun showLoadingDialog() {
        if (!loading.isShowing) {
            loading.show()
        }
    }

    /**
     * Hide dialog loading
     */
    open fun hideLoadingDialog() {
        if (loading.isShowing) {
            loading.dismiss()
        }
    }

    /**
     * Set cancelable dialog
     */
    fun setCancelableDialog(isCancelable: Boolean) {
        this.isCancelable = isCancelable
    }

    fun showMessageDialog(title: String?, message: Any) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        if (message is Int) {
            builder.setMessage(message)
        } else if (message is String) {
            builder.setMessage(message)
        }
        builder.setPositiveButton(android.R.string.ok, null)
        builder.setCancelable(true)
        builder.show()
    }

    fun showActionDialog(
        title: String?,
        message: Any,
        positiveAction: String,
        positiveListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        if (message is Int) {
            builder.setMessage(message)
        } else if (message is String) {
            builder.setMessage(message)
        }
        builder.setPositiveButton(positiveAction, positiveListener)
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.setCancelable(true)
        builder.show()
    }

    fun closeAllFragment() {
        while (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
    }


    override fun onResume() {
        super.onResume()
        getViewModel().onResume()
    }


    private fun statusBarBackgroundGradient() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            setBackgroundDrawableResource(R.drawable.bg_gradient)
        }
    }

    private fun loadingViewModel() {
        getViewModel().getLoading()?.observe(this, {
            if (it) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        })
    }

}