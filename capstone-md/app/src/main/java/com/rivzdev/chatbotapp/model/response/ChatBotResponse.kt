package com.rivzdev.chatbotapp.model.response

import com.google.gson.annotations.SerializedName

data class ChatBotResponse(

	@field:SerializedName("input")
	val input: String,

	@field:SerializedName("output")
	val output: String
)
