package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.workroute.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ConfirmLogin extends AppCompatActivity {

    private MaterialButton button_signUp;
    private MaterialCardView card_email,card_pass;
    private TextView password_for;
    private TextInputEditText ed_email,ed_password;
    private TextInputLayout layout_email,layout_password;
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
        layout_email=findViewById(R.id.layout_email);
        layout_password=findViewById(R.id.layout_pass);
        initListeners();
    }


    private void initListeners() {

        ed_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(card_email,ed_email,layout_email);
                }else{
                    removeColorsFocus(card_email,ed_email,layout_email);
                }
            }
        });

        ed_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(card_pass,ed_password,layout_password);
                }else{
                    removeColorsFocus(card_pass,ed_password,layout_password);
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

    private void setColorsFocus(MaterialCardView card,TextInputEditText ed,TextInputLayout txt){
        card.setStrokeColor(Color.parseColor("#0391FF"));
        card.setStrokeWidth(3);
        txt.setHintTextColor(ColorStateList.valueOf(getColor(R.color.secondary)));
        txt.setStartIconTintList(ColorStateList.valueOf(getColor(R.color.secondary)));
        ed.setTextColor(getColor(R.color.secondary));
        txt.setEndIconTintList(ColorStateList.valueOf(getColor(R.color.secondary)));
    }

    private void removeColorsFocus(MaterialCardView card,TextInputEditText ed,TextInputLayout txt){
        card.setStrokeWidth(0);
        ed.setTextColor(Color.parseColor("#BABABA"));
        txt.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
        txt.setStartIconTintList(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
        txt.setEndIconTintList(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
    }
}