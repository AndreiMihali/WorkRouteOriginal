package com.example.workroute.kotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.firebase.firestore.FirebaseFirestore

class ChatsActivity : AppCompatActivity() {
    private lateinit var toolbar:MaterialToolbar
    private lateinit var recycler:RecyclerView
    private lateinit var data:ArrayList<UserChatModel>
    private lateinit var adapter:AdapterChatsList
    private lateinit var firestore:FirebaseFirestore
    private lateinit var firebaseUser:FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Register)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        NetworkCallback().enable(this)
        init()
    }

    private fun init(){
        toolbar=findViewById(R.id.toolbar)
        recycler=findViewById(R.id.recycler_chats)
        recycler.layoutManager=LinearLayoutManager(applicationContext)
        firestore= FirebaseFirestore.getInstance()
        firebaseUser= FirebaseAuth.getInstance().currentUser!!
        data= ArrayList()
        initListeners()
        getData()
    }

    private fun getData() {
        Runnable {
            data.clear()
            firestore.collection("Usuarios").get().addOnSuccessListener {
                for(query in it){
                    val userId=query.get("uid").toString()
                    val userPhoto=query.get("fotoPerfil").toString()
                    val userName=query.get("nombre").toString()

                    if(userId!=null && userId != firebaseUser.uid){
                        data.add(UserChatModel(userName,userPhoto,userId,"Imbecil","20:40"))
                    }
                }
                adapter= AdapterChatsList(applicationContext,data)
                recycler.adapter=adapter
            }.addOnFailureListener {
                Toast.makeText(applicationContext,it.toString(),Toast.LENGTH_LONG).show()
            }
        }.run()
    }

    private fun initListeners() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }
}