package com.rivzdev.chatbotapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rivzdev.chatbotapp.databinding.ActivityHomePageBinding
import com.rivzdev.chatbotapp.ui.chat.ChatActivity

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonClicked()
    }

    private fun buttonClicked() {
        binding.apply {
            btnChat.setOnClickListener {
                startActivity(Intent(this@HomePage, ChatActivity::class.java))
            }

            btnFaq.setOnClickListener {
                val url = "https://m.traveloka.com/en-sg/help"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }
}