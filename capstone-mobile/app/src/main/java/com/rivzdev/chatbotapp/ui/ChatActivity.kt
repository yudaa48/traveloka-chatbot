package com.rivzdev.chatbotapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rivzdev.chatbotapp.databinding.ActivityChatBinding
import com.rivzdev.chatbotapp.databinding.ActivityHomePageBinding

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}