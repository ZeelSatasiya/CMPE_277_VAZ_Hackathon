package com.example.macroeconomicresearch.chat.model

import com.google.gson.annotations.SerializedName


data class ChatRequest(

    @SerializedName("sourceId") var sourceId: String? = null,
    @SerializedName("messages") var messages: List<Messages> = listOf()

)