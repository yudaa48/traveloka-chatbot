package com.rivzdev.chatbotapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object Time {

    @SuppressLint("SimpleDateFormat")
    fun timeStamp(): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd/MM")
        return simpleDateFormat.format(calendar.time)
    }
}