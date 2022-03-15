package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.workroute.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CreateAccount extends AppCompatActivity {

    private Toolbar toolbar;
    private MaterialCardView card_name,card_email,card_pass,card_confirmPass;
    private TextInputEditText ed_name,ed_email,ed_password,ed_confirmPassword;
    private MaterialButton create,log_in;
    private TextInputLayout layout_mail,layout_name,layout_pass,layout_passConfirm;
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
        card_pass=findViewById(R.id.card_password);
        card_confirmPass=findViewById(R.id.card_password_confirm);
        ed_name=findViewById(R.id.ed_username_register);
        ed_email=findViewById(R.id.ed_email_register);
        ed_password=findViewById(R.id.ed_pass_register);
        ed_confirmPassword=findViewById(R.id.ed_confirmpass_register);
        create=findViewById(R.id.button_create);
        log_in=findViewById(R.id.button_signin);
        layout_name=findViewById(R.id.layout_name);
        layout_mail=findViewById(R.id.layout_email);
        layout_pass=findViewById(R.id.layout_pass);
        layout_passConfirm=findViewById(R.id.layout_confirm_pass);
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
                    setColorsFocus(card_name,ed_name,layout_name);
                }else{
                    removeColorsFocus(card_name,ed_name,layout_name);
                }
            }
        });

        ed_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(card_email,ed_email,layout_mail);
                }else{
                    removeColorsFocus(card_email,ed_email,layout_mail);
                }
            }
        });

        ed_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    setColorsFocus(card_pass,ed_password,layout_pass);
                }else{
                    removeColorsFocus(card_pass,ed_password,layout_pass);
                }
            }
        });

        ed_confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    setColorsFocus(card_confirmPass,ed_confirmPassword,layout_passConfirm);
                }else{
                    removeColorsFocus(card_confirmPass,ed_confirmPassword,layout_passConfirm);
                }
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
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