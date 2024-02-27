package com.example.macroeconomicresearch.chat.di

import com.example.macroeconomicresearch.chat.data.api.ChatService
import com.example.macroeconomicresearch.chat.data.datasource.ChatDataSource
import com.example.macroeconomicresearch.chat.data.datasource.ChatDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SourcesModule {

    @Provides
    fun provideChatDataSource(chatService: ChatService): ChatDataSource {
        return ChatDataSourceImpl(chatService)
    }
}