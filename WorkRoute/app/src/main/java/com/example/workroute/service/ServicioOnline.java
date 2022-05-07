package com.example.workroute.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.workroute.companion.Companion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServicioOnline extends Service {

    private DatabaseReference reference;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("online").setValue("true");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuarios").child(Companion.user.getUid()).child("online").setValue("false");
        super.onDestroy();
    }

}
