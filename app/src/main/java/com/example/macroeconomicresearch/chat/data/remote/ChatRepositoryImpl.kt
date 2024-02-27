package com.example.macroeconomicresearch.chat.data.remote

import com.example.macroeconomicresearch.chat.data.util.SafeResult
import com.example.macroeconomicresearch.chat.data.datasource.ChatDataSource
import com.example.macroeconomicresearch.chat.model.ChatResponse
import com.example.macroeconomicresearch.chat.model.Messages
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource
) : ChatRepository {
    override suspend fun postChat(messages: List<Messages>): SafeResult<ChatResponse> {
        return when (val result = chatDataSource.postChat(messages)) {
            is SafeResult.Success -> SafeResult.Success(
                result.data
            )

            is SafeResult.Failure -> SafeResult.Failure(result.exception)
            SafeResult.NetworkError -> SafeResult.NetworkError
        }
    }
}
