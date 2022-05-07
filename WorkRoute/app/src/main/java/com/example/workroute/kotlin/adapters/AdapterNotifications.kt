package com.example.workroute.kotlin.adapters

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workroute.R
import com.example.workroute.kotlin.activities.ChatsActivity
import com.example.workroute.kotlin.model.NotificationItem
import com.example.workroute.profile.ActiveSubscriptions
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdapterNotifications(val data: ArrayList<NotificationItem>, val context: Context) :
    RecyclerView.Adapter<AdapterNotifications.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView
        val icon: ImageView
        val message: TextView

        init {
            card = itemView.findViewById(R.id.global_card)
            icon = itemView.findViewById(R.id.img_icon_notification)
            message = itemView.findViewById(R.id.txt_message)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (data[position].type) {
            "Message" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_chat_24)
                holder.icon.imageTintList =
                    ColorStateList.valueOf(context.getColor(R.color.secondary))
            }
            "Subscription" -> {
                holder.icon.setImageResource(R.drawable.ic_baseline_person_24)
                holder.icon.imageTintList =
                    ColorStateList.valueOf(context.getColor(R.color.secondary))
            }
            else -> {
                holder.icon.setImageResource(R.drawable.bx_error_alt)
                holder.icon.imageTintList =
                    ColorStateList.valueOf(context.getColor(R.color.secondary))
            }
        }
        holder.message.text = data[position].message

        if (data[position].read == "false") {
            holder.card.strokeColor = context.getColor(android.R.color.holo_red_dark)
            holder.card.strokeWidth = 1
        } else {
            holder.card.strokeWidth = 0
        }

        holder.itemView.setOnClickListener {
            when (data[position].type) {
                "Message" -> {
                    context.startActivity(Intent(context, ChatsActivity::class.java).apply {
                        flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                    setReadMessage(data[position].id!!)
                }
                "Subscription" -> {
                    context.startActivity(Intent(context, ActiveSubscriptions::class.java).apply {
                        flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                    setReadMessage(data[position].id!!)
                }
                else -> ""
            }
        }
    }

    private fun setReadMessage(id: String) {
        val childUpdates = hashMapOf<String, Any>()
        childUpdates["read"] = "true"
        FirebaseDatabase.getInstance().reference.child("Usuarios")
            .child(FirebaseAuth.getInstance().currentUser?.uid!!).child("Notifications").child(id)
            .updateChildren(childUpdates).addOnSuccessListener {
            }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}