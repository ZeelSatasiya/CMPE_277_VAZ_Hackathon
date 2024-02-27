package com.example.macroeconomicresearch.chat.data

import com.example.macroeconomicresearch.chat.constants.ApiKey
import com.example.macroeconomicresearch.chat.data.api.ChatService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun createHttpClient(): OkHttpClient {
        val requestInterceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("x-api-key", ApiKey)
                .build()
            return@Interceptor chain.proceed(request)
        }
        val httpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor)
        return httpClient.build()
    }

    fun createChatService(
        client: OkHttpClient
    ): ChatService {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.chatpdf.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatService::class.java)
    }
}