package com.example.macroeconomicresearch.chat.model

import com.google.gson.annotations.SerializedName

data class ChatResponse(

    @SerializedName("content") var content: String? = null

)