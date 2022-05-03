package com.example.workroute.service

import android.util.Log
import com.example.workroute.kotlin.model.NotificationItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class NotificationsInDatabase(var message:String,var read:String,var type:String,var receiverId:String): Thread() {
    override fun run() {
        val notificationModel=NotificationItem(
            message,
            type,
            read
        )

        FirebaseDatabase.getInstance().reference.child("Usuarios").child(receiverId).child("Notifications").push()
            .setValue(notificationModel).addOnSuccessListener {
                Log.d("SUCCESS","Success")
            }
    }
}