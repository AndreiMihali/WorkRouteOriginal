package com.example.workroute.kotlin.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.workroute.R
import com.example.workroute.activitys.MainActivity
import com.example.workroute.kotlin.adapters.AdapterMessages
import com.example.workroute.kotlin.model.MessageModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MessagesActivity : AppCompatActivity(){
    private lateinit var toolbar:MaterialToolbar
    private lateinit var profilePhoto:ImageView
    private lateinit var txtUserName:TextView
    private lateinit var recyclerMessages:RecyclerView
    private lateinit var edText:EditText
    private lateinit var fabButton:ImageButton
    private lateinit var firebaseUser:FirebaseUser
    private lateinit var reference:DatabaseReference
    private var adapterMessages:AdapterMessages?= null
    private lateinit var receiverId:String
    private lateinit var bundle:Intent
    private lateinit var nameUserString:String
    private lateinit var bundlePhoto:String
    private lateinit var data:ArrayList<MessageModel>
    private var isInTeChat=""
    private lateinit var txtIstyping:TextView
    private lateinit var status:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Register)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)
        init()
        isOnline()
    }

    override fun onStart() {
        super.onStart()
        FirebaseDatabase.getInstance().getReference("ChatList").child(receiverId).child(firebaseUser.uid)
            .child("isInChat").setValue("true")
        FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.uid).child(receiverId)
            .child("read").setValue("true")

    }

    override fun onStop() {
        super.onStop()
        FirebaseDatabase.getInstance().getReference("ChatList").child(receiverId).child(firebaseUser.uid)
            .child("isInChat").setValue("false")
    }


    private fun isOnline(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.value.toString()=="true"){
                    status.text="Online"
                }else{
                    status.text="Offline"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FirebaseDatabase.getInstance().getReference("Usuarios").child(receiverId).child("online").addValueEventListener(postListener)
    }

    private fun init(){
        toolbar=findViewById(R.id.toolbar_messages)
        profilePhoto=findViewById(R.id.profile_photo_messages)
        txtUserName=findViewById(R.id.user_name_messages)
        recyclerMessages=findViewById(R.id.recycler_mensajes)
        edText=findViewById(R.id.ed_message)
        fabButton=findViewById(R.id.btn_send)
        firebaseUser= FirebaseAuth.getInstance().currentUser!!
        reference= FirebaseDatabase.getInstance().reference
        bundle=intent
        receiverId= bundle.getStringExtra("userId")!!
        nameUserString= bundle.getStringExtra("userName")!!
        bundlePhoto= bundle.getStringExtra("userPhoto")!!
        data= ArrayList()
        val lm = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        lm.stackFromEnd = true
        recyclerMessages.layoutManager=lm
        txtIstyping=findViewById(R.id.txt_isTyping)
        status=findViewById(R.id.status)
        setData()
        initListeners()
        readChatList()
        isInTheChat()
        isTyping()
    }

    /***************************************************************************************************************
     *              AQUI EMPIEZAN LOS MÉTODOS QUE TIENEN QUE VER CON LOS MENSAJES
     ***************************************************************************************************************/

    private fun readChatList() {
        reference.child("Chats").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                for(snap in snapshot.children){
                    val message=snap.getValue(MessageModel::class.java)
                    if((message?.sender==firebaseUser.uid && message?.receiver==receiverId)
                        ||(message?.sender==receiverId && message?.receiver==firebaseUser.uid)){
                        data.add(message)
                    }
                }
                if(adapterMessages!=null){
                    adapterMessages?.notifyItemInserted(data.size-1)
                    recyclerMessages.layoutManager?.scrollToPosition(data.size-1)
                }else{
                    adapterMessages= AdapterMessages(applicationContext,data)
                    recyclerMessages.adapter=adapterMessages
                }

            }


            override fun onCancelled(error: DatabaseError) {}

        })

    }

    private fun sendTextMessage(message:String){
        Runnable {
            val currentDateTime= Calendar.getInstance()
            @SuppressLint("SimpleDateFormat")
            val currentTime=currentDateTime.time.toLocaleString()

            val t= Calendar.getInstance().time
            val formatt= SimpleDateFormat("HH:mm")
            val time=formatt.format(t)

            val messageModel=MessageModel(
                "$currentTime",
                message,
                firebaseUser.uid,
                receiverId,
                time.toString()
            )

            val map=HashMap<String,String>()
            map["messages"]=message

            reference.child("Chats").push().setValue(messageModel).addOnSuccessListener { Log.d(
                "Send",
                "onSuccess"
            )
            }.addOnFailureListener { e -> Log.d("Fail on send", "Failure " + e.message) }

            val chatRef=FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.uid).child(receiverId)
            chatRef.child("chat_id").setValue(receiverId)
            chatRef.child("message").setValue(message)
            chatRef.child("time").setValue(time.toString())
            chatRef.child("typing").setValue("false")
            chatRef.child("isInChat").setValue("false")

            val chatRef2=FirebaseDatabase.getInstance().getReference("ChatList").child(receiverId).child(firebaseUser.uid)
            chatRef2.child("chat_id").setValue(firebaseUser.uid)
            chatRef2.child("message").setValue(message)
            chatRef2.child("time").setValue(time.toString())
            chatRef2.child("typing").setValue("false")

            getSenderName(firebaseUser.uid,receiverId,message,nameUserString)
        }.run()
    }

    private fun getToken(sender:String,receiverId: String,message: String, title:String){
        var token=""
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                token=dataSnapshot.value.toString()
                sendNotification(token,message,title,sender)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        reference.child("Token").child(receiverId).addValueEventListener(postListener)
    }

    private fun getSenderName(sender: String, receiverId: String, message: String, s: String){
        FirebaseFirestore.getInstance().collection("Usuarios").document(sender).get().addOnSuccessListener {
            val name=it.get("nombre").toString()
            getToken(name,receiverId,message,s)
        }
    }

    private fun isInTheChat(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children){
                    isInTeChat=dataSnapshot.child("isInChat").value.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        reference.child("ChatList").child(firebaseUser.uid).child(receiverId).addValueEventListener(postListener)
    }

    private fun sendNotification(token: String, message: String, title: String, user: String) {

        if(isInTeChat=="false"||isInTeChat==""){
            Runnable {
                FirebaseDatabase.getInstance().getReference("ChatList").child(receiverId).child(firebaseUser.uid)
                    .child("read").setValue("false")
                var myRequest=Volley.newRequestQueue(applicationContext)
                var json=JSONObject()

                json.put("to",token)
                val notification=JSONObject()

                notification.put("titulo",title)
                notification.put("detalle","$message")
                notification.put("activityOpen","MessagesActivity");

                json.put("data",notification)

                val url="https://fcm.googleapis.com/fcm/send"

                val jsonRequest: JsonObjectRequest = object :
                    JsonObjectRequest(Method.POST, url, json, object : Response.Listener<JSONObject?> {
                        override fun onResponse(response: JSONObject?) {

                            //now handle the response
                        }
                    }, object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError) {
                            error.printStackTrace()
                        }
                    }) {
                    //this is the part, that adds the header to the request
                    override fun getHeaders(): Map<String, String> {
                        val map=HashMap<String,String>()
                        map["content-type"] = "application/json"
                        map["authorization"]="key=AAAAt-8FdV8:APA91bEmHhrJazEV2AB7LWBxhcRib1wMtuAGsobZydq6OUOCXYmaKpkuhnyHFFLdb3Eg5h2VqE134NChAJGNTWracREdIiLDIxPpHvnNAxxbw-MUw6_C-WHRspmbu1GEeUp5p418RPyp"
                        return map
                    }
                }

                myRequest.add(jsonRequest)

            }.run()
        }
    }

    /***************************************************************************************************************
     *              AQUI ACABAN LOS MÉTODOS QUE TIENEN QUE VER CON LOS MENSAJES
     ***************************************************************************************************************/

    private fun setData() {
        receiverId.let {
            txtUserName.text=nameUserString
            if(bundlePhoto==""){
                profilePhoto.setImageDrawable(getDrawable(R.drawable.default_user_login))
            }else{
                Glide.with(applicationContext).load(bundlePhoto).into(profilePhoto)
            }
        }
    }

    private fun initListeners(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

        edText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (TextUtils.isEmpty(edText.text.toString())) {
                    fabButton.visibility=View.GONE
                    reference.child("ChatList").child(receiverId).child(firebaseUser.uid)
                        .child("typing").setValue("false")
                } else {
                    fabButton.visibility=View.VISIBLE
                    reference.child("ChatList").child(receiverId).child(firebaseUser.uid)
                        .child("typing").setValue("true")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        fabButton.setOnClickListener {
            if(!TextUtils.isEmpty(edText.text.toString())){
                sendTextMessage(edText.text.toString())
                edText.setText("")
            }
        }

    }

    private fun isTyping(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.value.toString()=="true"){
                    status.visibility=View.GONE
                    txtIstyping.visibility=View.VISIBLE
                }else{
                    txtIstyping.visibility=View.GONE
                    status.visibility=View.VISIBLE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        reference.child("ChatList").child(firebaseUser.uid).child(receiverId).child("typing").addValueEventListener(postListener)
    }

}