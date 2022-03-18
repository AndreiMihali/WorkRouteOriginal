package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.workroute.R;

public class suscripcionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Subscribe);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suscripciones);
    }
}