package com.example.macroeconomicresearch.chat.di

import com.example.macroeconomicresearch.chat.data.ApiClient
import com.example.macroeconomicresearch.chat.data.api.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideOkHttpClient() = ApiClient.createHttpClient()

    @Provides
    @Singleton
    fun provideChatService(client: OkHttpClient): ChatService =
        ApiClient.createChatService(client)
}