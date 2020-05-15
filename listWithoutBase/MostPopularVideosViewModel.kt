package com.luzlaura98.example.ui

import androidx.lifecycle.MutableLiveData
import com.luzlaura98.example.data.entities.VideoBJJ
import com.luzlaura98.example.remote.APIClient
import com.luzlaura98.example.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Luz on 15/05/2020.
 */
class MostPopularVideosViewModel : BaseViewModel() {

    private val compositeDisposableVideos = CompositeDisposable()
    val videosLiveData: MutableLiveData<List<VideoBJJ>> = MutableLiveData()

    fun getMostPopularVideos(page: Int) {
        compositeDisposableVideos.add(
            APIClient.getClient()
                .getVideos()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    videosLiveData.postValue(it)
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