package com.example.workroute.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.activitys.MainActivity;
import com.example.workroute.adapters.ActiveSubAdapter;
import com.example.workroute.driverActivities.DriverMap;
import com.example.workroute.kotlin.activities.MessagesActivity;
import com.example.workroute.model.SubscribedUser;
import com.example.workroute.service.NotificationService;
import com.example.workroute.service.NotificationsInDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActiveSubscriptions extends AppCompatActivity implements ActiveSubAdapter.MyItemClickListener{

    private MaterialToolbar toolbarSubs;
    private ArrayList <SubscribedUser> data;
    private ActiveSubAdapter adapter=null;
    private RecyclerView recyclerView;
    private ArrayList<User> customersId;
    private ProgressBar progressBar;
    private TextView txt_null;
    private BottomSheetBehavior bottomSheetBehavior;
    private ProgressDialog progressDialog;

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
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.sheet));
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        progressDialog=new ProgressDialog(this,R.style.ProgressDialog);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        getData();
    }

    private void displayInformationCustomer(int state) {
        TextView txt_subscriber=findViewById(R.id.txt_name_subscriber);
        MaterialButton btn_accept=findViewById(R.id.btn_accept);
        MaterialButton btn_decline=findViewById(R.id.btn_decline);
        ImageButton btn_message=findViewById(R.id.button_message);
        TextView txt_customerHome=findViewById(R.id.txt_homeAddressCustomer);
        TextView txt_customerWork=findViewById(R.id.txt_workAddressCustomer);
        ImageView userPhoto=findViewById(R.id.profile_photo_sheet);
        LinearLayout lnUnsubscribed=findViewById(R.id.ln_accept_or_decline);
        MaterialButton btn_cancel=findViewById(R.id.btn_cancel);
        TextView totalPay=findViewById(R.id.txt_total_pay_travel);

        if(data.get(adapter.itemSelected).getStatus().equals("pending")){
            txt_subscriber.setText(data.get(adapter.itemSelected).getUserName()+" wants to subscribe to you");
            btn_cancel.setVisibility(View.GONE);
            lnUnsubscribed.setVisibility(View.VISIBLE);
            totalPay.setText("20$/month");
        }else if(data.get(adapter.itemSelected).getStatus().equals("accepted")){
            txt_subscriber.setText(data.get(adapter.itemSelected).getUserName()+" is already subscribed to you");
            lnUnsubscribed.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.VISIBLE);
            totalPay.setText("20$/month");
        }else if(data.get(adapter.itemSelected).getStatus().equals("declined")||data.get(adapter.itemSelected).getStatus().equals("canceled")){
            txt_subscriber.setText("You declined or canceled this user. Ask the user to send you another request");
            lnUnsubscribed.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
            totalPay.setText("0$/month");
        }

        if(data.get(adapter.itemSelected).getProfilePhoto().equals("")){
            userPhoto.setImageResource(R.drawable.default_user_login);
        }else{
            Glide.with(this).load(data.get(adapter.itemSelected).getProfilePhoto()).into(userPhoto);
        }

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Usuarios").child(data.get(adapter.itemSelected).getUserUid());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            txt_customerHome.setText(snapshot.child("localidad").getValue().toString());
                            txt_customerWork.setText(snapshot.child("workAddress").getValue().toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActiveSubscriptions.this, MessagesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("userId", data.get(adapter.itemSelected).getUserUid())
                        .putExtra("userName", data.get(adapter.itemSelected).getUserName())
                        .putExtra("userPhoto", data.get(adapter.itemSelected).getProfilePhoto());
                startActivity(intent);
            }
        });

        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStateSubscription("declined");
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStateSubscription("accepted");
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCancel();
            }
        });

        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(state);
        bottomSheetBehavior.setSaveFlags(BottomSheetBehavior.SAVE_ALL);
    }

    private void showDialogCancel(){
        new MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setCancelable(false)
                .setTitle("Confirm cancel")
                .setMessage("If you cancel you will stop receiving payments from this user. Are you sure you want to continue?")
                .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateStateSubscription("canceled");
                    }
                })
                .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


    private void updateStateSubscription(String status){
        progressDialog.show();
        DatabaseReference referenceDriverRequest=FirebaseDatabase.getInstance().getReference("Drivers").child(FirebaseAuth.getInstance().getUid()).child("Requests").child(data.get(adapter.itemSelected).getUserUid());
        DatabaseReference referenceCustomerRequest=FirebaseDatabase.getInstance().getReference("Customers").child(data.get(adapter.itemSelected).getUserUid()).child("Requests").child(data.get(adapter.itemSelected).getUserUid());
        HashMap<String,Object> map=new HashMap<>();
        map.put("userId",data.get(adapter.itemSelected).getUserUid());
        map.put("status",status);
        referenceDriverRequest.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("userId",data.get(adapter.itemSelected).getUserUid());
                    map.put("status",status);
                    referenceCustomerRequest.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                getSenderName(FirebaseAuth.getInstance().getUid(),data.get(adapter.itemSelected).getUserUid(),status);
                            }else{
                                progressDialog.dismiss();
                            }
                        }
                    });
                }else{
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.e("ERROR EN EL ENVIO DE PETICION",e.toString());
            }
        });
    }

    private void getSenderName(String sender,String receiverId,String status){
        FirebaseFirestore.getInstance().collection("Usuarios").document(sender).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name=documentSnapshot.get("nombre").toString();
                if(status.equals("canceled")){
                    getToken(name,receiverId,name+" "+status+" your subscription","REQUEST "+status);
                }else{
                    getToken(name,receiverId,name+" "+status+" your request","REQUEST "+status);
                }
            }
        });
    }

    private void getToken(String sender,String receiver,String message, String title){
        FirebaseDatabase.getInstance().getReference().child("Token").child(receiver).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tokenUser=snapshot.getValue().toString();
                sendNotification(tokenUser,message,title,sender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(String token,String message, String title,String receiver) {
        new NotificationService(getApplicationContext(),token,message,title,receiver,"ActiveSubscriptions").start();
        new NotificationsInDatabase(message,"false","Subscription",data.get(adapter.itemSelected).getUserUid()).start();
        displayInformationCustomer(BottomSheetBehavior.STATE_HIDDEN);
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
                            data.add(new SubscribedUser(photo,name,direction,customer.getStatus(),customer.getUi()));
                            if(adapter!=null){
                                adapter.notifyDataSetChanged();
                            }else{
                                adapter= new ActiveSubAdapter(data,getApplicationContext());
                                setListenerToAdapter();
                                recyclerView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        });
    }

    private void setListenerToAdapter(){
        adapter.setMyItemClickListener(this);
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

    @Override
    public void setOnItemClick(View view) {
        displayInformationCustomer(BottomSheetBehavior.STATE_EXPANDED);
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
