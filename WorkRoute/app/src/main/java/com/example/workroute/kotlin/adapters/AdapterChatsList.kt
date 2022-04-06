package com.example.workroute.kotlin.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workroute.R
import com.example.workroute.kotlin.activities.MessagesActivity
import com.example.workroute.kotlin.model.UserChatModel
import com.google.android.material.card.MaterialCardView

class AdapterChatsList(val context: Context,val data:ArrayList<UserChatModel>): RecyclerView.Adapter<AdapterChatsList.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profilePhoto:ImageView
        val txtName:TextView
        val txtLastMessage:TextView
        val txtLastMessageHour:TextView
        val card_general:MaterialCardView

        init {
            profilePhoto=itemView.findViewById(R.id.profile_photo_chat_list)
            txtName=itemView.findViewById(R.id.txt_user_name_chat_list)
            txtLastMessage=itemView.findViewById(R.id.txt_lastMessage_chat_list)
            txtLastMessageHour=itemView.findViewById(R.id.txt_last_message_time_chat_list)
            card_general=itemView.findViewById(R.id.card_general)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.chats_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(data[position].profilePhoto==""){
            holder.profilePhoto.setImageResource(R.drawable.default_user_login)
        }else{
            Glide.with(context).load(data[position].profilePhoto).into(holder.profilePhoto)
        }
        holder.txtName.text=data[position].name

        if(data[position].lastMessage.isNullOrEmpty()){
            holder.txtLastMessage.text=null
        }else{
            holder.txtLastMessage.text=data[position].lastMessage
        }

        if(data[position].lastMessageHour.isNullOrEmpty()){
            holder.txtLastMessageHour.text=null
        }else{
            holder.txtLastMessageHour.text=data[position].lastMessageHour
        }

        if(data[position].read=="false"){
            holder.card_general.strokeColor=context.getColor(R.color.secondary)
            holder.card_general.strokeWidth=2
        }else{
            holder.card_general.strokeWidth=0
        }

        val user=data[position]
        holder.itemView.setOnClickListener{
            context.startActivity(Intent(context, MessagesActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("userId",user.userUID)
                    .putExtra("userName",user.name)
                    .putExtra("userPhoto",user.profilePhoto)
            })
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}