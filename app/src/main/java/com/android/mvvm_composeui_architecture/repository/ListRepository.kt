package com.android.mvvm_composeui_architecture.repository

import com.android.mvvm_composeui_architecture.network.TestApi

/**
 * @author kevin
 * @create 2023/2/10
 * @description
 */
class ListRepository {

  suspend fun getList() = TestApi.indexService.getData()

}