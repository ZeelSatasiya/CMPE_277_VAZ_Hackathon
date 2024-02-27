package com.example.macroeconomicresearch.chat.data.datasource

import com.example.macroeconomicresearch.chat.data.util.SafeResult
import com.example.macroeconomicresearch.chat.model.ChatResponse
import com.example.macroeconomicresearch.chat.model.Messages

interface ChatDataSource {
    suspend fun postChat(
        messages: List<Messages>
    ): SafeResult<ChatResponse>
}