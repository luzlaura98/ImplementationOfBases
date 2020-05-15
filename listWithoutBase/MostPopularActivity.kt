package com.luzlaura98.example.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.luzlaura98.example.R
import com.luzlaura98.example.data.entities.VideoBJJ
import com.luzlaura98.example.ui.MostPopularVideosViewModel
import com.luzlaura98.example.ui.adapters.VideoBJJAdapter
import com.luzlaura98.example.ui.base.BaseActivity
import com.luzlaura98.example.ui.main.videoDetail.VideoDetailActivity
import com.luzlaura98.example.ui.utils.EndlessScrollListener
import com.luzlaura98.example.ui.utils.gone
import kotlinx.android.synthetic.main.activity_toolbar_rv.*

class MostPopularActivity : BaseActivity() {

    lateinit var model: MostPopularVideosViewModel
    lateinit var adapter: VideoBJJAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_rv)
        initToolbar(getString(R.string.most_popular), true)
        setupAdapter()
        setupListenerItemsPerPage()
        initViewModel()
    }

    private fun setupAdapter() {
        adapter = VideoBJJAdapter {
            VideoDetailActivity.start(this, it)
        }
        recyclerView.adapter = adapter
    }

    /**
     * At first, adapter was created and show the first items from page 1
     * because of this listener call onLoadMore() after "there are something to scroll".
     * Note: currentPage = 1
     * */
    private fun setupListenerItemsPerPage() {
        recyclerView.addOnScrollListener(object :
            EndlessScrollListener(recyclerView.layoutManager!!) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                model.getMostPopularVideos(currentPage)
            }

            override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
        }.apply { currentPage = 1 })
    }

    override fun initViewModel() {
        model = ViewModelProvider(this)[MostPopularVideosViewModel::class.java]
        model.init(this)
        model.videosLiveData.observe(this, Observer { showVideosPerPage(it) })
        model.showMessageLiveData.observe(this, Observer { showPlaceholderText(it) })
        model.getMostPopularVideos(1)
        showLoading(true)
    }

    private fun showVideosPerPage(items: List<VideoBJJ>) {
        adapter.items.addAll(items)
        adapter.notifyDataSetChanged()
        showLoading(false)
    }

    override fun showPlaceholderText(text: String?) {
        super.showPlaceholderText(text)
        recyclerView.gone()
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

}
