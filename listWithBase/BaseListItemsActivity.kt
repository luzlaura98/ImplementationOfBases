package com.luzlaura98.example.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.luzlaura98.example.R
import com.luzlaura98.example.ui.adapters.BasicAdapter
import com.luzlaura98.example.ui.utils.EndlessScrollListener
import com.luzlaura98.example.ui.utils.gone
import kotlinx.android.synthetic.main.activity_toolbar_rv.*

abstract class BaseListItemsActivity<DATA, ADAPTER : BasicAdapter<DATA>, MODEL : BaseItemListViewModel<DATA>> : BaseActivity() {

    lateinit var model : MODEL
    lateinit var adapter : ADAPTER

    /**
     * layoutResId is changeable, but it must have
     * a recycler view with id: 'recyclerView'
     * */
    @get:LayoutRes
    open var layoutResId: Int = R.layout.activity_toolbar_rv

    @get:StringRes
    open var titleResId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        initToolbar(if (titleResId != null) getString(titleResId!!) else null,true)
        setupAdapter()
        setupListenerItemsPerPage()
        initViewModel()
    }
    
    private fun setupAdapter() {
        adapter = instantiateAdapter()
        recyclerView.adapter = adapter
    }

    abstract fun instantiateAdapter(): ADAPTER

    /**
     * At first, adapter was created and show the first items from page 1
     * because of this listener call onLoadMore() after "there are something to scroll".
     * Note: currentPage = 1
     * */
    private fun setupListenerItemsPerPage() {
        recyclerView.addOnScrollListener(object :
            EndlessScrollListener(recyclerView.layoutManager!!) {
            override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                model.getItems(currentPage)
            }

            override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
        }.apply { currentPage = 1 })
    }

    override fun initViewModel() {
        model = instantiateViewModel()
        model.init(this)
        model.itemsLiveData.observe(this, Observer { showItemsPerPage(it) })
        model.showMessageLiveData.observe(this, Observer { showPlaceholderText(it) })
        model.getItems(1)
        showLoading(true)
    }

    abstract fun instantiateViewModel(): MODEL

    private fun showItemsPerPage(items: List<DATA>) {
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
