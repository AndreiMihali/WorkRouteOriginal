package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.workroute.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Login);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}