package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.workroute.R;
import com.example.workroute.network.callback.NetworkCallback;

public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        new NetworkCallback().enable(this);
    }

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(Notifications.this).start();
        super.onDestroy();
    }
}