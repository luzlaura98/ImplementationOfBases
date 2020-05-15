package com.luzlaura98.example.ui.base

/**
 * Created by Luz on 24/04/2020.
 */
interface Placeholder {
    //fun hidePlaceholder()
    fun showPlaceholderText(text: String? = null)
    fun showLoading(isLoading: Boolean)
    fun showSnackbarMessage(text: String)
}