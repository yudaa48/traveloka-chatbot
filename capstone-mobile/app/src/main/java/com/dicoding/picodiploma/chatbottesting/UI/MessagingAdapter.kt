package com.dicoding.picodiploma.chatbottesting.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.chatbottesting.Data.Message
import com.dicoding.picodiploma.chatbottesting.Utils.Constants.RECEIVED_ID
import com.dicoding.picodiploma.chatbottesting.Utils.Constants.SEND_ID
import com.dicoding.picodiploma.chatbottesting.databinding.MessageItemBinding

class MessagingAdapter:RecyclerView.Adapter <MessagingAdapter.MessageViewHolder>(){

    var messageList = mutableListOf<Message>()
    inner class MessageViewHolder( val binding : MessageItemBinding) : RecyclerView.ViewHolder( binding.root){
      init{
          itemView.setOnClickListener{
            messageList.removeAt(bindingAdapterPosition)
              notifyItemRemoved(bindingAdapterPosition)
          }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = MessageItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MessageViewHolder((view))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageList[position]

        when (currentMessage.id) {
            SEND_ID -> {
                holder.binding.tvMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.binding.tvBotMessage.visibility = View.GONE
            }
            RECEIVED_ID -> {
                holder.binding.tvBotMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.binding.tvMessage.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun insertMessage(message: Message){
       this.messageList.add(message)
       notifyItemInserted(messageList.size)
        notifyDataSetChanged()
    }
}