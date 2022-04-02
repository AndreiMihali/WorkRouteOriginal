package com.example.workroute.kotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workroute.R
import com.example.workroute.kotlin.adapters.AdapterChatsList
import com.example.workroute.kotlin.model.UserChatModel
import com.example.workroute.network.callback.NetworkCallback
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class ChatsActivity : AppCompatActivity() {
    private lateinit var toolbar:MaterialToolbar
    private lateinit var recycler:RecyclerView
    private lateinit var data:ArrayList<UserChatModel>
    private  var adapter:AdapterChatsList?=null
    private lateinit var firestore:FirebaseFirestore
    private lateinit var firebaseUser:FirebaseUser
    private lateinit var reference:DatabaseReference
    private lateinit var allUsers:ArrayList<String>
    private lateinit var progressCircular:ProgressBar
    private lateinit var txtNull:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Register)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        NetworkCallback().enable(this)
        init()
    }

    private fun init(){
        toolbar=findViewById(R.id.toolbar)
        txtNull=findViewById(R.id.txt_null)
        progressCircular=findViewById(R.id.progress_circular)
        recycler=findViewById(R.id.recycler_chats)
        recycler.layoutManager=LinearLayoutManager(applicationContext)
        firestore= FirebaseFirestore.getInstance()
        firebaseUser= FirebaseAuth.getInstance().currentUser!!
        reference= FirebaseDatabase.getInstance().reference
        data= ArrayList()
        data.reverse()
        allUsers= ArrayList()
        initListeners()
        if(firebaseUser!=null){
            getData()
        }


    }

    private fun getData() {
        progressCircular.visibility=View.VISIBLE
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                allUsers.clear()
                for(snapshot in dataSnapshot.children){
                    val userId= Objects.requireNonNull(snapshot.child("chat_id").value.toString())
                    allUsers.add(userId)
                }
                progressCircular.visibility=View.GONE
                if(allUsers.isEmpty()){
                    txtNull.visibility=View.VISIBLE
                }else{
                    txtNull.visibility=View.GONE
                    getUserData()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressCircular.visibility=View.GONE
            }
        }
        reference.child("ChatList").child(firebaseUser.uid).addValueEventListener(postListener)
    }

    private fun getUserData() {
        Handler().post {
            for(userId in allUsers){
                firestore.collection("Usuarios").document(userId).get().addOnSuccessListener {
                    val userId=it.get("uid").toString()
                    val userPhoto=it.get("fotoPerfil").toString()
                    val userName=it.get("nombre").toString()

                    data.add(UserChatModel(userName,userPhoto,userId,"Imbecil","20:40"))

                    if(adapter!=null){
                        adapter?.notifyItemInserted(0)
                        adapter?.notifyDataSetChanged()
                    }else{
                        adapter= AdapterChatsList(applicationContext,data)
                        recycler.adapter=adapter
                    }
                }

            }
        }
    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }
}