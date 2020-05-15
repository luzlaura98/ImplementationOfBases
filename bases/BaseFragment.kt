package com.luzlaura98.example.ui.base

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.luzlaura98.example.R
import com.luzlaura98.example.ui.utils.gone
import com.luzlaura98.example.ui.utils.visible
import kotlinx.android.synthetic.main.layout_placeholder_no_videos.*
import kotlinx.android.synthetic.main.layout_placeholder_no_videos.view.*

/**
 * Created by Luz on 08/04/2020.
 */

abstract class BaseFragment : Fragment(), Placeholder {

    val TAG = this::class.java.simpleName

    /**
     * @param menuResId menu to inflate
     * override onOptionsItemSelected if there's menu.
     * */
    @get:MenuRes
    open var menuResId: Int? = null

    @get:LayoutRes
    open var layoutResId: Int? = null

    protected fun getBase() = (activity as? BaseActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (menuResId != null)
            setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        if (layoutResId == null)
            super.onCreateView(inflater, container, savedInstanceState)
        else
            inflater.inflate(layoutResId!!, container, false)

    open fun initViewModel() {}

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            placeholderContainer.visible()
            placeholderContainer.loading.visible()
            placeholderContainer.ivPlaceholder.gone()
            placeholderContainer.tvPlaceholder.gone()
        } else {
            placeholderContainer.gone()
        }
    }

    override fun showPlaceholderText(text: String?) {
        placeholderContainer.visible()
        placeholderContainer.loading.gone()
        placeholderContainer.ivPlaceholder.visible()
        placeholderContainer.tvPlaceholder.visible()
        placeholderContainer.tvPlaceholder.text =
            text ?: context?.getString(R.string.placeholder_error_gen)
    }

    override fun showSnackbarMessage(text: String) {
        if (view == null) return
        val snack = Snackbar.make(view!!,text,Snackbar.LENGTH_LONG)
        snack.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        if (menuResId != null)
            inflater.inflate(menuResId!!, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroyView() {
        Log.i(TAG,"onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i(TAG,"onDestroy")
        super.onDestroy()
    }

}