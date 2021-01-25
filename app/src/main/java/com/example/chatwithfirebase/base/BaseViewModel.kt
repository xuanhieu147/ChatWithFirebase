package com.example.chatwithfirebase.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    companion object{
        const val SHOW_TOAST = -2
        const val FINISH_ACTIVITY = -1
        const val BACK = 0
        const val SHOW_ERROR = 1
    }

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mIsLoading = SingleLiveData<Boolean>()
    val uiEventLiveData = SingleLiveData<Int>()
    var errorMessage: Any = "Unknown error"
    var toastMessage: Any = "Unknown message"

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun launch(job: () -> Disposable) {
        compositeDisposable.add(job())
    }

    fun isLoading(): Boolean{
        return mIsLoading.value ?: false
    }

    fun getLoading(): SingleLiveData<Boolean>? {
        return mIsLoading
    }

    fun setLoading(isLoading: Boolean) {
        mIsLoading.value = isLoading
    }

//    open fun handleError(t: Throwable): Any {
//        if (t is HttpException) {
//            t.response()?.run {
//                errorBody()?.let { error ->
//                    error.contentType()?.let { mediaType ->
//                        if (mediaType.toString().contains(Define.CONTENT_TYPE_JSON)) {
//                            val errorResult = error.string()
//                            try {
//                                val message = gson.fromJson(errorResult, Error::class.java)
//                                return message.error
//                            }catch (e: JsonSyntaxException){
//                                LogUtil.error(e.toString())
//                            }
//                        }
//                    }
//                }
//            }
//        }else if(t is UnknownHostException){
//            return R.string.connection_error_message
//        }
//        return R.string.generic_error_message
//    }
//
//    open fun handleResponse(response: Response<Void>): Any {
//        response.errorBody()?.let { error ->
//            error.contentType()?.let { mediaType ->
//                if (mediaType.toString().contains(Define.CONTENT_TYPE_JSON)) {
//                    val errorResult = error.string()
//                    try {
//                        val message = gson.fromJson(errorResult, Error::class.java)
//                        return message.error
//                    } catch (e: JsonSyntaxException) {
//                        LogUtil.error(e.toString())
//                    }
//                }
//            }
//        }
//        return R.string.generic_error_message
//    }

//    fun showError(message: Any){
//        errorMessage = message
//        uiEventLiveData.value = SHOW_ERROR
//    }

    fun performBack() {
        uiEventLiveData.value = BACK
    }

    open fun onStart(){}
    open fun onStop(){}
    open fun onResume(){}
}