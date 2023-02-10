package com.android.mvvm_composeui_architecture.network

/**
 * @author kevin
 * @create 2023/2/9
 * @description
 */
object TestApi {

    val retrofit
        get() =
            RequestManager.instance.retrofit

    val indexService: IndexService
        get() = RequestManager.instance
            .retrofit
            .create(IndexService::class.java)

}