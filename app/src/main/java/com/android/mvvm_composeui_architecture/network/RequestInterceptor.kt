package com.android.mvvm_composeui_architecture.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException
import kotlin.jvm.Throws

class RequestInterceptor(private val context: Context?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (context == null) {
            throw NullPointerException("Please call RequestManager.getInstance().init(context) first")
        }
        val request = chain.request()
        val builder = request.newBuilder()
        val urlBuilder = request.url.newBuilder()
        val httpUrl = urlBuilder.build()
        val newRequest = builder.url(httpUrl).build()

        return try {
            chain.proceed(newRequest)
        } catch (e: Exception) {
            chain.proceed(request)
        }
    }
}
