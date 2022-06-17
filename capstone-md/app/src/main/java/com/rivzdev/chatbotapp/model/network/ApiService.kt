package com.rivzdev.chatbotapp.model.network

import com.rivzdev.chatbotapp.model.response.ChatBotResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers(
        "Content-Type: application/json"
    )
    @POST("/predict")
    fun getChatBot(
        @Body chatBotResponse: ChatBotResponse
    ): Call<ChatBotResponse>
}