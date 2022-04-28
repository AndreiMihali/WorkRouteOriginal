package com.example.workroute.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.driverActivities.AddDriverInformation;
import com.example.workroute.driverActivities.DriverMap;
import com.example.workroute.model.User;
import com.example.workroute.network.callback.NetworkCallback;
import com.example.workroute.service.ServicioOnline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private MaterialButton button_customer,button_driver;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NetworkCallback().enable(this);
        getToken();
        init();
        startService(new Intent(this, ServicioOnline.class));
        getUserData();
    }

    private void init(){
        button_customer=findViewById(R.id.btn_customer);
        button_driver=findViewById(R.id.btn_driver);
        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        initListeners();
    }

    private void initListeners() {
        button_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDriver()){
                    showDialog("Configure driver profile","Before you can continue, set up your driver profile",new Intent(MainActivity.this, AddDriverInformation.class));
                }else{
                    startActivity(new Intent(MainActivity.this, DriverMap.class));
                }
            }
        });

        button_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CustomerMap.class));
            }
        });
    }

    private void showDialog(String title,String message,Intent intent){
        new MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        // Get new FCM registration token
                        String token = task.getResult();
                        saveToken(token);
                    }
                });
    }

    private void saveToken(String token) {
        reference = FirebaseDatabase.getInstance().getReference().child("Token");
        reference.child(FirebaseAuth.getInstance().getUid()).setValue(token);
    }


    public static class Destroy extends Thread{

        private Activity activity;

        public Destroy(Activity activity){
            this.activity=activity;
        }

        @Override
        public void run() {
            activity.stopService(new Intent(activity,ServicioOnline.class));
        }
    }

    private void getUserData(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                firestore.collection("Usuarios").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Companion.user = documentSnapshot.toObject(User.class);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ERROR AL OBTENER LOS DATOS DEL USUARIO","ERROR AL OBTENER LOS DATOS DEL USUARIO "+e);
                    }
                });
            }
        });
    }

    private Boolean isDriver(){
        return (Companion.user.isConductor())?true:false;
    }

    private Boolean isSubscribed(){
        return (Companion.user.isSuscrito())?true:false;
    }

}