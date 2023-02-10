package com.android.mvvm_composeui_architecture.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.mvvm_composeui_architecture.network.TestData
import com.android.mvvm_composeui_architecture.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author kevin
 * @create 2023/2/9
 * @description
 */
@HiltViewModel
class ListViewModel @Inject constructor(
    private val listRepository: ListRepository,
) : BaseViewModel() {

    private val _list = MutableLiveData<List<TestData>>()
    val list: LiveData<List<TestData>>
        get() = _list

    private val _arg = MutableLiveData<TestData>()
    val arg: LiveData<TestData>
        get() = _arg

    fun getData() {
        viewModelScope.launch {
            listRepository.getList().apply {
                when (isSuccessful) {
                    true -> body()?.let {
                        _list.postValue(it)
                    }
                    else -> {}
                }
            }
        }
    }

    fun sendData(data: TestData) {
        _arg.postValue(data)
    }

}