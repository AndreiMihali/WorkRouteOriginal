package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.workroute.R;
import com.example.workroute.network.callback.NetworkCallback;

public class suscripcionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Subscribe);
        super.onCreate(savedInstanceState);
        new NetworkCallback().enable(this);
        setContentView(R.layout.layout_suscripciones);
    }
}