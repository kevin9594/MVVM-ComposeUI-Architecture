package com.android.mvvm_composeui_architecture.network

import retrofit2.Response
import retrofit2.http.GET

/**
 * @author kevin
 * @create 2023/2/9
 * @description
 */
interface IndexService {
    @GET("movielist.json")
    suspend fun getData() : Response<List<TestData>>
}