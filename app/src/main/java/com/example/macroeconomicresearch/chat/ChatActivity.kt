package com.example.macroeconomicresearch.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.macroeconomicresearch.chat.ui.ChatScreen
import com.example.macroeconomicresearch.chat.ui.ChatViewModel
import com.example.macroeconomicresearch.chat.ui.model.ChatUiModel
import com.example.macroeconomicresearch.chat.ui.theme.MacroeconomicResearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel: ChatViewModel by viewModels()

        super.onCreate(savedInstanceState)
        setContent {
            val conversation = viewModel.conversation.collectAsState()
            MacroeconomicResearchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(
                        model = ChatUiModel(
                            content = conversation.value,
                            role = ChatUiModel.Author.bot
                        ),
                        onSendChatClickListener = { msg -> viewModel.sendChat(msg) },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}
