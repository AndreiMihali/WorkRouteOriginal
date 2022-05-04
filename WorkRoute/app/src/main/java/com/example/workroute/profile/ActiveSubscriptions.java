package com.example.workroute.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.workroute.R;
import com.example.workroute.activitys.MainActivity;
import com.example.workroute.adapters.ActiveSubAdapter;
import com.example.workroute.model.SubscribedUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActiveSubscriptions extends AppCompatActivity {

    private MaterialToolbar toolbarSubs;
    private ArrayList <SubscribedUser> data;
    private ActiveSubAdapter adapter=null;
    private RecyclerView recyclerView;
    private ArrayList<User> customersId;
    private ProgressBar progressBar;
    private TextView txt_null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_subscriptions);
        main();

    }

    private void main() {
         controls();
         listeners();
    }



    private void controls() {
        toolbarSubs = findViewById(R.id.toolbar_subs);
        recyclerView=findViewById(R.id.recyclerSubs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data=new ArrayList<>();
        txt_null=findViewById(R.id.txt_null);
        progressBar=findViewById(R.id.progress_circular);
        customersId=new ArrayList<>();
        getData();
    }

    private void getData(){
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Drivers").child(FirebaseAuth.getInstance().getUid()).child("Requests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                customersId.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    String userId=  snap.child("userId").getValue().toString();
                    String status=  snap.child("status").getValue().toString();
                    customersId.add(new User(userId,status));
                }
                progressBar.setVisibility(View.GONE);

                if(customersId.isEmpty()){
                    txt_null.setVisibility(View.VISIBLE);
                }else{
                    txt_null.setVisibility(View.GONE);
                    getCustomerInformation();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCustomerInformation(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                for(User customer:customersId){
                    FirebaseDatabase.getInstance().getReference().child("Usuarios").child(customer.getUi()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String photo=dataSnapshot.child("fotoPerfil").getValue().toString();
                            String name=dataSnapshot.child("nombre").getValue().toString();
                            String direction=dataSnapshot.child("workAddress").getValue().toString();
                            data.add(new SubscribedUser(photo,name,direction,customer.getStatus()));
                            if(adapter!=null){
                                adapter.notifyDataSetChanged();
                            }else{
                                adapter= new ActiveSubAdapter(data,getApplicationContext());
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        });
    }


        private void listeners() {

            toolbarSubs.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                    finish();
                }
            });


        }

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(ActiveSubscriptions.this).start();
        super.onDestroy();
    }

}
class User{
    private String ui;
    private String status;

    public User(){

    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String ui, String status) {
        this.ui = ui;
        this.status = status;
    }
}
