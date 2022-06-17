package com.rivzdev.chatbotapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rivzdev.chatbotapp.model.network.ApiConfig
import com.rivzdev.chatbotapp.model.response.ChatBotResponse
import com.rivzdev.chatbotapp.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotViewModel: ViewModel() {

    private val _message = MutableLiveData<ChatBotResponse>()
    val message: LiveData<ChatBotResponse> = _message

    fun getMessageBot(input: ChatBotResponse) {
        val client = ApiConfig.getApiService().getChatBot(input)

        client.enqueue(object : Callback<ChatBotResponse> {
            override fun onResponse(
                call: Call<ChatBotResponse>,
                response: Response<ChatBotResponse>
            ) {
                if (response.isSuccessful ) {
                     _message.value = response.body()
                    Log.d(Constant.TAG, "onResponse: ${response.body()}")
                } else {
                    Log.d(Constant.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChatBotResponse>, t: Throwable) {
                Log.d(Constant.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}