package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.workroute.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button button_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Login);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        button_login=findViewById(R.id.button_login);

        //Metodo para inilializar todos los escuchadores de eventos
        initListeners();
    }

    private void initListeners() {
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ConfirmLogin.class));
                finish();
            }
        });

    }
}