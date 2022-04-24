package com.example.workroute.suscribtion.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workroute.R
import com.example.workroute.model.SubscriptionItem

class SubscriptionAdapter(val data:ArrayList<SubscriptionItem>, var context:Context): RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val txt_title:TextView
       val txt_price:TextView
       val txt_description:TextView

        init {
            txt_title=itemView.findViewById(R.id.txt_title)
            txt_price=itemView.findViewById(R.id.txt_price)
            txt_description=itemView.findViewById(R.id.txt_description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.subscription_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_title.text=data[position].titleSubscription
        holder.txt_title.setBackgroundColor(Color.parseColor(data[position].colorBackground))
        holder.txt_price.text=data[position].price
        holder.txt_title.setBackgroundColor(Color.parseColor(data[position].colorBackground))
        holder.txt_description.text=data[position].descriptionSubscription
    }

    override fun getItemCount(): Int {
        return data.size
    }
}