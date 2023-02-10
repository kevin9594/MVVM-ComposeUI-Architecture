package com.android.mvvm_composeui_architecture.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * @author kevin
 * @create 2023/2/8
 * @description
 */
class MainViewModel : BaseViewModel() {

    private val _result = MutableSharedFlow<Int>()
    val result = _result.asSharedFlow()

    private var count = 0

    fun plusOne() {
        viewModelScope.launch {
            count++
            _result.emit(count)
        }
    }

}