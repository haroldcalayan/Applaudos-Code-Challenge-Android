package com.haroldcalayan.mubi.util

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

inline fun <reified T>provideApi(apiUrl: String, httpClient: OkHttpClient): T {
    return Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create(T::class.java)
}