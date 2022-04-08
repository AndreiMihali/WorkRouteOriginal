package com.example.workroute.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Provider;
import java.util.List;
import java.util.Map;

public class ServicioOnline extends Service{

    private DatabaseReference reference;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        reference= FirebaseDatabase.getInstance().getReference();
        reference.child("Usuarios").child(FirebaseAuth.getInstance().getUid()).child("online").setValue("true");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        reference.child("Usuarios").child(FirebaseAuth.getInstance().getUid()).child("online").setValue("false");
        super.onDestroy();
    }

}
