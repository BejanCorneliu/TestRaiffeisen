package com.raiffeisen.bejancorneliu.tools

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.raiffeisen.bejancorneliu.screens.first.FirstViewModel

class ViewModelFactory(private val nContext: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirstViewModel::class.java)) {
            return FirstViewModel(nContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}