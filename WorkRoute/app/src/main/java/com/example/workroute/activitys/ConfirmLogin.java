package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.workroute.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ConfirmLogin extends AppCompatActivity {

    private MaterialButton button_signUp;
    private MaterialCardView card_email,card_pass;
    private TextView password_for;
    private EditText ed_email,ed_password;
    private MaterialButton buttton_login,button_google;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_login);
        init();
    }

    private void init(){
        button_signUp=findViewById(R.id.button_signup);
        card_email=findViewById(R.id.card_email);
        card_pass=findViewById(R.id.card_password);
        password_for=findViewById(R.id.password_forgot);
        ed_email=findViewById(R.id.ed_email);
        ed_password=findViewById(R.id.ed_pass);
        buttton_login=findViewById(R.id.button_login);
        button_google=findViewById(R.id.button_google);
        initListeners();
    }

    private void initListeners() {

        ed_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(card_email,ed_email);
                }else{
                    removeColorsFocus(card_email,ed_email);
                }
            }
        });

        ed_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(card_pass,ed_password);
                }else{
                    removeColorsFocus(card_pass,ed_password);
                }
            }
        });


        button_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConfirmLogin.this,CreateAccount.class));
            }
        });
    }

    private void setColorsFocus(MaterialCardView card,EditText ed){
        card.setStrokeColor(Color.parseColor("#0391FF"));
        card.setStrokeWidth(3);
        ed.setTextColor(getColor(R.color.secondary));
        ed.setHintTextColor(getColor(R.color.secondary));
        ed.setCompoundDrawableTintList(ColorStateList.valueOf(getColor(R.color.secondary)));
    }

    private void removeColorsFocus(MaterialCardView card,EditText ed){
        card.setStrokeWidth(0);
        ed.setTextColor(Color.parseColor("#BABABA"));
        ed.setHintTextColor(Color.parseColor("#D5D5D5"));
        ed.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#BABABA")));
    }
}