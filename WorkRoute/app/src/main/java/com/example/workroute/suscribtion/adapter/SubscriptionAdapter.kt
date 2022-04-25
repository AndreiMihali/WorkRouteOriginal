package com.example.workroute.suscribtion.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workroute.R
import com.example.workroute.model.SubscriptionItem
import com.google.android.material.card.MaterialCardView

class SubscriptionAdapter(val data:ArrayList<SubscriptionItem>, var context:Context): RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {

    var itemSelectedIndex=-1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
       val txt_title:TextView
       val txt_price:TextView
       val txt_description:TextView
       val cardGlobal:MaterialCardView

        init {
            txt_title=itemView.findViewById(R.id.txt_title)
            txt_price=itemView.findViewById(R.id.txt_price)
            txt_description=itemView.findViewById(R.id.txt_description)
            cardGlobal=itemView.findViewById(R.id.card_global)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemSelectedIndex=adapterPosition
            notifyDataSetChanged()
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

        if(itemSelectedIndex==position){
            holder.cardGlobal.animate().scaleX(1.1f).scaleY(1.1f)
            holder.cardGlobal.strokeColor=Color.parseColor("#DA0909")
            holder.cardGlobal.strokeWidth=3
        }else{
            holder.cardGlobal.animate().scaleX(1.0f).scaleY(1.0f)
            holder.cardGlobal.strokeWidth=0
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}