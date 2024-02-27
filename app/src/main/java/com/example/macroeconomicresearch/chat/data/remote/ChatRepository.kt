package com.example.macroeconomicresearch.chat.data.remote

import com.example.macroeconomicresearch.chat.data.util.SafeResult
import com.example.macroeconomicresearch.chat.model.ChatResponse
import com.example.macroeconomicresearch.chat.model.Messages

interface ChatRepository {
    suspend fun postChat(messages: List<Messages>): SafeResult<ChatResponse>
}