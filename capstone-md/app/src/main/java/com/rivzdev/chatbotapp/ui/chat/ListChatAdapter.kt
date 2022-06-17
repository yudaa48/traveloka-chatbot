package com.rivzdev.chatbotapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivzdev.chatbotapp.databinding.ItemChatRowBinding
import com.rivzdev.chatbotapp.model.response.ChatBotResponse

class ListChatAdapter: RecyclerView.Adapter<ListChatAdapter.ViewHolder>() {
    private val list = ArrayList<ChatBotResponse>()

    inner class ViewHolder(private val binding: ItemChatRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatBotResponse) {
            with(binding) {
                tvCustomer.text = chat.input
                tvBot.text = chat.output
                if (!tvCustomer.text.isNullOrEmpty()) {
                    tvBot.visibility = View.GONE
                    tvCustomer.visibility = View.VISIBLE
                }

                if (!tvBot.text.isNullOrEmpty()) {
                    tvCustomer.visibility = View.GONE
                    tvBot.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
    fun addChatToList(chat: ChatBotResponse) {
        list.add(chat)
        notifyDataSetChanged()
    }
}