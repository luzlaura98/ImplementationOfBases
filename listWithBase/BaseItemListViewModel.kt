package com.luzlaura98.example.ui.base

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Luz on 15/05/2020.
 */
abstract class BaseItemListViewModel<DATA> : BaseViewModel() {

    private val compositeDisposableVideos = CompositeDisposable()
    val itemsLiveData: MutableLiveData<List<DATA>> = MutableLiveData()

    abstract fun getItemsObservable(page : Int): Observable<List<DATA>>

    fun getItems(page: Int) {
        compositeDisposableVideos.add(
            getItemsObservable(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    itemsLiveData.postValue(it)
                }, {
                    if (page == 1) onError(it)
                })
        )
    }

    override fun onCleared() {
        compositeDisposableVideos.clear()
        super.onCleared()
    }

}