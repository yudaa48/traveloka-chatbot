package com.rivzdev.chatbotapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rivzdev.chatbotapp.R
import com.rivzdev.chatbotapp.ui.home.HomePage
import com.rivzdev.chatbotapp.utils.Constant

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, HomePage::class.java))
            finish()
        }, Constant.time)
    }
}