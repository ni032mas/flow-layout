package com.ni032mas.sample.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ni032mas.sample.R
import com.ni032mas.sample.entities.Message

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.VH>() {

    private val data = mutableListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setMessages(messages: MutableList<Message>) {
        data.clear()
        data.addAll(messages)
        notifyDataSetChanged()
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage = itemView.findViewById<TextView>(R.id.textMessage)
        private val textTime = itemView.findViewById<TextView>(R.id.textTime)
        fun bind(message: Message) {
            textMessage.text = message.text
            textTime.text = message.time
        }
    }
}