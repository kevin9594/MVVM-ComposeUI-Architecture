package com.android.mvvm_composeui_architecture.network

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

const val CONNECT_TIMEOUT: Long = 20 * 1000
const val WRITE_TIMEOUT: Long = 20 * 1000
const val READ_TIMEOUT: Long = 20 * 1000

class RequestManager private constructor(context: Context) {

    companion object {
        private lateinit var staticContext: Application
        val instance: RequestManager by lazy {
            RequestManager(staticContext)
        }

        fun init(context: Application) {
            staticContext = context
        }
    }

    var retrofit: Retrofit

    private val mOkHttpClientBuilder: OkHttpClient.Builder = getUnsafeOkHttpClient()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        .addInterceptor(RequestInterceptor(context))
        .addInterceptor(HttpLogInterceptor())

    private val mMoshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    init {
        retrofit = createRetrofit("https://howtodoandroid.com/apis/")
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(mOkHttpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create(mMoshi))
            .build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        return try {
            val trustAllCerts: Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?, authType: String?,
                    ) {
                        checkServerTrusted(chain)
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?, authType: String?,
                    ) {
                        checkServerTrusted(chain)
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            val sslContext: SSLContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Throws(CertificateException::class)
    fun checkServerTrusted(chain: Array<X509Certificate?>?) {
        chain?.let {
            try {
                chain[0]?.checkValidity()
            } catch (e: java.lang.Exception) {
                throw CertificateException("Certificate not valid or trusted.")
            }
        }
    }
}
