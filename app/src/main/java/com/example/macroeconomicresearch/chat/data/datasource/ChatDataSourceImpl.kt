package com.example.macroeconomicresearch.chat.data.datasource

import android.util.Log
import com.example.macroeconomicresearch.chat.data.util.SafeResult
import com.example.macroeconomicresearch.chat.data.util.safeApiCall
import com.example.macroeconomicresearch.chat.constants.SourceId
import com.example.macroeconomicresearch.chat.data.api.ChatService
import com.example.macroeconomicresearch.chat.model.ChatRequest
import com.example.macroeconomicresearch.chat.model.ChatResponse
import com.example.macroeconomicresearch.chat.model.Messages
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ChatDataSourceImpl(
    private val chatService: ChatService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ChatDataSource {

    override suspend fun postChat(messages: List<Messages>): SafeResult<ChatResponse> {
        messages.drop(0)
        Log.d("test", messages.toString())
        return safeApiCall(dispatcher) {
            chatService.chat(ChatRequest(sourceId = SourceId, messages = messages))
        }
    }
}