package com.example.workroute.service

import android.telecom.Call
import android.util.Log
import com.example.workroute.kotlin.model.NotificationItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationsInDatabase(var message:String,var read:String,var type:String,var receiverId:String): Thread() {
    override fun run() {
        val notificationModel=NotificationItem(
            message,
            type,
            read
        )

        existNot(notificationModel, object : Callback<Boolean> {
            override fun setData(value: Boolean) {
                if(!value){
                    FirebaseDatabase.getInstance().reference.child("Usuarios").child(receiverId)
                        .child("Notifications").push()
                        .setValue(notificationModel).addOnSuccessListener {
                            Log.d("SUCCESS", "Success")
                        }
                }
            }
        })
    }

    private fun existNot(notificationItem: NotificationItem,callback: Callback<Boolean>){
        var exist=false
        val listener=object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children){
                    if(snap.child("message").value.toString()==notificationItem.message){
                        exist=true
                        break
                    }
                }
                callback.setData(exist)
            }
            override fun onCancelled(error: DatabaseError) {
               Log.d("ERROR","errroR")
            }
        }
        FirebaseDatabase.getInstance().reference.child("Usuarios").child(receiverId)
            .child("Notifications").addValueEventListener(listener)
    }

    interface Callback<T>{
        fun setData(value:T)
    }
}