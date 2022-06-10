package com.dicoding.picodiploma.chatbottesting.UI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.chatbottesting.Data.Message
import com.dicoding.picodiploma.chatbottesting.Utils.BotResponse
import com.dicoding.picodiploma.chatbottesting.Utils.Constants.OPEN_GOOGLE
import com.dicoding.picodiploma.chatbottesting.Utils.Constants.OPEN_SEARCH
import com.dicoding.picodiploma.chatbottesting.Utils.Constants.RECEIVED_ID
import com.dicoding.picodiploma.chatbottesting.Utils.Constants.SEND_ID
import com.dicoding.picodiploma.chatbottesting.Utils.Time
import com.dicoding.picodiploma.chatbottesting.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: MessagingAdapter

    private val botList = listOf("July","Budi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val random = (0..1).random()
        customMessage("Hello ! Today you're speaking with ${botList[random]}, how may i help ?")

        recyclerview()

        clickEvents()
    }

    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVED_ID, timeStamp))
                binding.apply{
                    rvMessages.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }

    private fun clickEvents(){
        binding.apply {
            buttonSend.setOnClickListener {
                sendMessages()
            }

            etMessage.setOnClickListener{
                GlobalScope.launch {
                    delay(1000)
                    withContext(Dispatchers.Main) {
                        rvMessages.scrollToPosition(adapter.itemCount - 1)
                    }
                }
            }
        }
    }

    override fun onStart(){
        super.onStart()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                binding.apply {
                    rvMessages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }

    private fun recyclerview(){
        adapter = MessagingAdapter()
        binding.apply {
            rvMessages.adapter = adapter
            rvMessages.layoutManager = LinearLayoutManager(applicationContext)
        }
    }

    private fun sendMessages(){
        binding.apply {
            val message = etMessage.text.toString()
            val timeStamp = Time.timeStamp()

            if (message.isNotEmpty()){
                etMessage.setText("")

                adapter.insertMessage(Message(message, SEND_ID, timeStamp))
                rvMessages.scrollToPosition(adapter.itemCount-1)

                botResponse(message)
            }
        }
    }

    private fun botResponse(message: String){
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){

                val response = BotResponse.basicResponses(message)

                adapter.insertMessage(Message(response, RECEIVED_ID, timeStamp))
                binding.apply{
                    rvMessages.scrollToPosition(adapter.itemCount-1)
                }

                when(response){
                    OPEN_GOOGLE ->{
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH ->{
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm : String? = message.substringAfter("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }

    }



}