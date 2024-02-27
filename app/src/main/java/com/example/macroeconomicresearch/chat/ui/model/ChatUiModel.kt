package com.example.macroeconomicresearch.chat.ui.model

data class ChatUiModel(
    val content: List<Message>,
    val role: Author,
) {
    data class Message(
        val text: String,
        val author: Author,
    ) {
        val isFromMe: Boolean
            get() = author.id == MY_ID

        companion object {
            val initConv = Message(
                text = "Ask me anything",
                author = Author.bot
            )
        }
    }

    data class Author(
        val id: String,
        val name: String
    ) {
        companion object {
            val bot = Author("1", "assistant")
            val me = Author(MY_ID, "user")
        }
    }

    companion object {
        const val MY_ID = "-1"
    }
}