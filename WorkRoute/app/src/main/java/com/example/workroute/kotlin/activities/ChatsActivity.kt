package com.example.workroute.kotlin.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workroute.R
import com.example.workroute.activitys.MainActivity.Destroy
import com.example.workroute.kotlin.adapters.AdapterChatsList
import com.example.workroute.kotlin.model.UserChatModel
import com.example.workroute.network.callback.NetworkCallback
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ChatsActivity : AppCompatActivity() {
    private lateinit var toolbar:MaterialToolbar
    private lateinit var recycler:RecyclerView
    private lateinit var data:ArrayList<UserChatModel>
    private  var adapter:AdapterChatsList?=null
    private lateinit var firestore:FirebaseFirestore
    private lateinit var firebaseUser:FirebaseUser
    private lateinit var reference:DatabaseReference
    private lateinit var allUsers:ArrayList<UserList>
    private lateinit var progressCircular:ProgressBar
    private lateinit var txtNull:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Register)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        NetworkCallback().enable(this)
        init()
    }


    override fun onDestroy() {
        Destroy(this).start()
        super.onDestroy()
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
        allUsers= ArrayList()
        initListeners()
        if(firebaseUser!=null){
            getData()
        }
    }

    override fun onRestart() {
        super.onRestart()
        getData()
    }

    private fun getData() {
        progressCircular.visibility=View.VISIBLE
        allUsers.clear()
        data.clear()
        adapter?.notifyDataSetChanged()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                allUsers.clear()
                data.clear()
                adapter?.notifyDataSetChanged()
                for(snapshot in dataSnapshot.children){
                    val userId= Objects.requireNonNull(snapshot.child("chat_id").value.toString())
                    val lastMessage=snapshot.child("message").value.toString()
                    val time=snapshot.child("time").value.toString()
                    val read=snapshot.child("read").value.toString()
                    allUsers.add(UserList(userId,lastMessage,time,read))
                }
                progressCircular.visibility=View.GONE
                if(allUsers.isEmpty()){
                    txtNull.visibility=View.VISIBLE
                }else{
                    txtNull.visibility=View.GONE
                    recycler.removeAllViewsInLayout()
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
            for(user in allUsers){
                firestore.collection("Usuarios").document(user.userId).get().addOnSuccessListener {
                    val userId=it.get("uid").toString()
                    val userPhoto=it.get("fotoPerfil").toString()
                    val userName=it.get("nombre").toString()
                    data.add(UserChatModel(userName,userPhoto,userId,user.lastMessage,user.time,user.read))
                    if(adapter!=null){
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

    /*private void startAutocompleteIntent(){
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setTypeFilter(TypeFilter.CITIES)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }*/
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Toast.makeText(getApplicationContext(),place.getName(),Toast.LENGTH_LONG).show();
                locality.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    private data class UserList(val userId:String,val lastMessage: String,val time:String,val read:String)
}