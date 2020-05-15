package com.luzlaura98.example.ui.base

import android.content.Intent
import android.view.Menu
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.luzlaura98.example.R
import com.luzlaura98.example.data.entities.VideoBJJ
import com.luzlaura98.example.ui.dialogs.ViewProductDialog
import com.luzlaura98.example.ui.main.viewproduct.ViewProductActivity
import com.luzlaura98.example.ui.utils.gone
import com.luzlaura98.example.ui.utils.visible
import kotlinx.android.synthetic.main.layout_placeholder_no_videos.*
import kotlinx.android.synthetic.main.layout_placeholder_no_videos.view.*
import kotlinx.android.synthetic.main.toolbar_default.*

/**
 * Created by Luz on 08/04/2020.
 */

abstract class BaseActivity : AppCompatActivity(), Placeholder {
    val TAG = this::class.java.simpleName

    @get:MenuRes
    open var menuResId: Int? = null

    var underline: Boolean = true
        set(value) {
            field = value
            if (value)
                toolbarContainer?.setBackgroundResource(R.drawable.bg_underline_gray)
            else
                toolbarContainer?.foreground = null
        }

    var currentTitle: String? = null
        set(value) {
            if (!value.isNullOrEmpty())
                field = value
            tvToolbarTitle?.text = value
        }

    fun initSearchToolbar() {
        toolbar?.gone()
        ivLogo?.gone()
        tvToolbarTitle?.gone()
        etSearch?.visible()
    }

    fun initLogoToolbar() {
        toolbar?.gone()
        ivLogo?.visible()
        tvToolbarTitle?.gone()
        etSearch?.gone()
    }

    fun initToolbar(title: String?, backEnabled: Boolean = false) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        toolbar?.visible()
        ivLogo?.gone()
        tvToolbarTitle?.visible()
        etSearch?.gone()
        currentTitle = title
        if (backEnabled) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun initToolbar(title: String, @DrawableRes indicator: Int) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        toolbar?.visible()
        ivLogo?.gone()
        tvToolbarTitle?.visible()
        etSearch?.gone()
        currentTitle = title
        supportActionBar?.setHomeAsUpIndicator(indicator)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun changeColorToolbar(color : Int){
        toolbarContainer?.setBackgroundColor(color)
        toolbarContainer?.foreground = null;
    }

    open fun customMenu(menu: Menu) {}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menuResId != null) {
            menuInflater.inflate(menuResId!!, menu)
            customMenu(menu!!)
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    open fun initViewModel(){}

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
            text ?: getString(R.string.placeholder_error_gen)
    }

    override fun showSnackbarMessage(text: String) {
        val snack = Snackbar.make(findViewById(android.R.id.content),text, Snackbar.LENGTH_LONG)
        snack.show()
    }

    fun showViewProductDialog(videoBJJ : VideoBJJ){
        var dialog : AlertDialog? = null
        dialog = ViewProductDialog(this, videoBJJ){
            startActivity(Intent(this,
                ViewProductActivity::class.java))
        }.show()
    }
}