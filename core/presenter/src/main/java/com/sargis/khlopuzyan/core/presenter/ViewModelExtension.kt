package com.sargis.khlopuzyan.core.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.runOnBackground(block: suspend () -> Unit): Job =
    viewModelScope.launch(Dispatchers.Default) {
        Log.e("LOG_TAG", Thread.currentThread().name)
        block()
    }

fun ViewModel.runOnIO(block: suspend () -> Unit): Job =
    viewModelScope.launch(Dispatchers.IO) {
        block()
    }

fun ViewModel.runOnUI(block: suspend () -> Unit): Job =
    viewModelScope.launch(Dispatchers.Main) {
        block()
    }