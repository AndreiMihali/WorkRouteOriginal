package com.example.workroute.kotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workroute.R
import com.example.workroute.kotlin.model.MessageModel
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AdapterMessages(val context: Context, val data: ArrayList<MessageModel>) :
    RecyclerView.Adapter<AdapterMessages.ViewHolder>() {
    private lateinit var firebaseUser: FirebaseUser

    companion object {
        val MSG_TYPE_LEFTT = 0
        val MSG_TYPE_RIGHT = 1
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var message: TextView
        private var time: TextView
        private var card_chat: MaterialCardView
        private var layout_content: LinearLayout

        init {
            message = itemView.findViewById(R.id.txt_message)
            time = itemView.findViewById(R.id.txt_hora)
            card_chat = itemView.findViewById(R.id.card_chat)
            layout_content = itemView.findViewById(R.id.layout_content)
        }

        fun bind(messageModel: MessageModel) {
            message.text = messageModel.textMessage
            time.text = messageModel.time
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = if (viewType == MSG_TYPE_LEFTT) {
            LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        return if (data[position].sender == firebaseUser.uid) {
            MSG_TYPE_RIGHT
        } else {
            MSG_TYPE_LEFTT
        }
    }
}