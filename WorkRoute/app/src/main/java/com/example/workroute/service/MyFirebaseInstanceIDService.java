package com.example.workroute.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.workroute.R;
import com.example.workroute.activitys.MainActivity;
import com.example.workroute.kotlin.activities.ChatsActivity;
import com.example.workroute.kotlin.activities.MessagesActivity;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private DatabaseReference reference;
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e("token","mi token es: "+token);
        saveToken(token);
    }

    private void saveToken(String token) {
        reference= FirebaseDatabase.getInstance().getReference().child("Token");
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            reference.child(FirebaseAuth.getInstance().getUid()).setValue(token);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        String from=message.getFrom();
        Log.e("tag","Mensaje recibido de "+from);

        if(message.getNotification()!=null){
            Log.e("tag","el titulo es: "+message.getNotification().getTitle());
            Log.e("tag","el cuerpo es: "+message.getNotification().getBody());
        }

        if(message.getData().size()>0){
            String titulo=message.getData().get("titulo");
            String detalle=message.getData().get("detalle");
            String activityOpen=message.getData().get("activityOpen");
            mayorQueOreo(titulo,detalle,activityOpen);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void mayorQueOreo(String titulo, String detalle,String activityOpen){
        String id="mensaje";
        NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,id);

        NotificationChannel channel=new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
        channel.setShowBadge(true);
        assert nm!=null;
        nm.createNotificationChannel(channel);

        try {
            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(titulo)
                    .setSmallIcon(R.mipmap.work_icon)
                    .setContentText(detalle)
                    .setContentIntent(clickNotify())
                    .setContentInfo("Nuevo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Random random=new Random();
        int idNotify=random.nextInt(80000);

        assert nm!=null;
        nm.notify(idNotify,builder.build());
    }

    public PendingIntent clickNotify() throws ClassNotFoundException {
        Intent nf=new Intent(getApplicationContext(), ChatsActivity.class);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }
}
