package com.example.internetrestaurant.di

import com.example.internetrestaurant.BaseApplication.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createRetrofitModule(): Module = module {
    single { initRetrofit(createOkHttp()) }
}

private fun createOkHttp(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            return@Interceptor chain.proceed(
                chain.request()
                    .newBuilder()
                    .build()
            )
        })
        .addInterceptor(interceptor)
        .build()
}

private fun initRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .callFactory(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create()).build()