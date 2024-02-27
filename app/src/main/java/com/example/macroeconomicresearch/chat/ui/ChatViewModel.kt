package com.example.macroeconomicresearch.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.macroeconomicresearch.chat.data.remote.ChatRepository
import com.example.macroeconomicresearch.chat.data.util.SafeResult
import com.example.macroeconomicresearch.chat.model.Messages
import com.example.macroeconomicresearch.chat.ui.model.ChatUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    val conversation: StateFlow<List<ChatUiModel.Message>>
        get() = _conversation
    private val _conversation = MutableStateFlow(
        listOf(ChatUiModel.Message.initConv)
    )

    fun sendChat(msg: String) {
        val myChat = ChatUiModel.Message(msg, ChatUiModel.Author.me)
        viewModelScope.launch(Dispatchers.IO) {
            _conversation.emit(_conversation.value + myChat)
            when (val result = chatRepository.postChat(messages = _conversation.value.map {
                Messages(
                    role = it.author.name,
                    content = it.text
                )
            })) {
                is SafeResult.Success -> {
                    result.data.let { safeResult ->
                        _conversation.emit(
                            _conversation.value + ChatUiModel.Message(
                                text = safeResult.content!!,
                                author = ChatUiModel.Author.bot
                            )
                        )
                    }
                }

                is SafeResult.Failure -> {

                }

                SafeResult.NetworkError -> {

                }
            }
        }
    }
}