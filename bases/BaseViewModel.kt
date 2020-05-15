package com.luzlaura98.example.ui.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luzlaura98.example.R
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Created by Luz on 19/03/2020.
 */
open class BaseViewModel : ViewModel() {
    protected var contextRef: WeakReference<Context>? = null
    protected var disposable: Disposable? = null
    val showMessageLiveData = MutableLiveData<String>()
    val TAG = this::class.java.simpleName

    open fun init(context: Context) {
        contextRef = WeakReference(context)
    }

    fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        showMessage(contextRef?.get()?.getString(R.string.placeholder_error_gen)?:"Something was wrong")//ApiError
    }

    fun showMessage(message: String) {
        showMessageLiveData.postValue(message)
    }

    override fun onCleared() {
        Log.i("ON_CLEARED","disposing")
        disposable?.dispose()
        contextRef = null;
        super.onCleared()
    }
}