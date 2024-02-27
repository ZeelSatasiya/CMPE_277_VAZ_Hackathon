package com.example.macroeconomicresearch.chat.data.api

import com.example.macroeconomicresearch.chat.model.ChatRequest
import com.example.macroeconomicresearch.chat.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {

    @POST("chats/message")
    suspend fun chat(@Body request: ChatRequest): ChatResponse

}