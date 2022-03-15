package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.workroute.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class CreateAccount extends AppCompatActivity {

    private Toolbar toolbar;
    private MaterialCardView card_name,card_email,card_locality,card_pass,card_confirmPass;
    private EditText ed_name,ed_email,ed_country,ed_password,ed_confirmPassword;
    private MaterialButton create,log_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        init();
    }

    private void init(){
        toolbar=findViewById(R.id.toolbar);
        card_name=findViewById(R.id.card_name);
        card_email=findViewById(R.id.card_email);
        card_locality=findViewById(R.id.card_locality);
        card_pass=findViewById(R.id.card_password);
        card_confirmPass=findViewById(R.id.card_password_confirm);
        ed_name=findViewById(R.id.ed_username_register);
        ed_email=findViewById(R.id.ed_email_register);
        ed_country=findViewById(R.id.ed_locality_register);
        ed_password=findViewById(R.id.ed_pass_register);
        ed_confirmPassword=findViewById(R.id.ed_confirmpass_register);

        initListeners();
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        ed_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(card_name,ed_name);
                }else{
                    removeColorsFocus(card_name,ed_name);
                }
            }
        });

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

        ed_country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    setColorsFocus(card_locality,ed_country);
                }else{
                    removeColorsFocus(card_locality,ed_country);
                }
            }
        });

        ed_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    setColorsFocus(card_pass,ed_password);
                }else{
                    removeColorsFocus(card_pass,ed_password);
                }
            }
        });

        ed_confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    setColorsFocus(card_confirmPass,ed_confirmPassword);
                }else{
                    removeColorsFocus(card_confirmPass,ed_confirmPassword);
                }
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