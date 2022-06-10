package com.dicoding.picodiploma.chatbottesting.HomePage


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.picodiploma.chatbottesting.UI.MainActivity
import com.dicoding.picodiploma.chatbottesting.databinding.ActivityHomePageBinding



class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private var  url = "https://www.traveloka.com/id-id/help"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater
        )
        setContentView(binding.root)

        binding.moveToChatbot.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}