package com.example.deber03_recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChatItem(
    val name: String,
    val lastMessage: String,
    val date: String,
    val profileImageUrl: String,
    val statusImageUrl: String
)

class ChatAdapter(private val chatList: List<ChatItem>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProfile: ImageView = itemView.findViewById(R.id.imageProfile)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvLastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val statusImage: ImageView = itemView.findViewById(R.id.statusImage)

        fun bind(chatItem: ChatItem) {
            tvName.text = chatItem.name
            tvLastMessage.text = chatItem.lastMessage
            tvDate.text = chatItem.date

            Glide.with(imageProfile.context)
                .load(chatItem.profileImageUrl).circleCrop()
                .into(imageProfile)

            Glide.with(statusImage.context)
                .load(chatItem.statusImageUrl).circleCrop()
                .into(statusImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatItem = chatList[position]
        holder.bind(chatItem)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}