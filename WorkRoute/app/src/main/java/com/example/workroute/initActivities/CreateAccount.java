package com.example.workroute.initActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.workroute.R;
import com.example.workroute.model.User;
import com.example.workroute.model.Viaje;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateAccount extends AppCompatActivity {

    private Toolbar toolbar;
    private MaterialCardView card_name,card_email,card_pass,card_confirmPass;
    private TextInputEditText ed_name,ed_email,ed_password,ed_confirmPassword;
    private MaterialButton create;
    private TextView log_in;
    private TextInputLayout layout_mail,layout_name,layout_pass,layout_passConfirm;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        new NetworkCallback().enable(this);
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
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this,R.style.ProgressDialog);
        progressDialog.setMessage("Please wait...");
        ed_name.requestFocus();
        reference= FirebaseDatabase.getInstance().getReference();
        setColorsFocus(card_name,ed_name,layout_name);
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

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData(v);
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

    private void checkData(View v){
        if(ed_name.getText().toString().isEmpty()||ed_email.getText().toString().isEmpty()||ed_password.getText().toString().isEmpty()||
            ed_confirmPassword.getText().toString().isEmpty()){
            showSnackbar("You must fill al fields",v);
            clearFields(ed_name,ed_email,ed_password,ed_confirmPassword);
        }else if(!ed_password.getText().toString().trim().equals(ed_confirmPassword.getText().toString().trim())){
            showSnackbar("The passwords must be the same",v);
            clearFields(ed_password,ed_confirmPassword);
        }else{
            createAccount(v);
            progressDialog.show();
        }
    }

    private void showSnackbar(String message,View v){
        Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
    }

    private void clearFields(EditText ... ed){
        for(int i=0;i<ed.length;i++){
            ed[i].setText("");
        }
        ed[0].requestFocus();
    }

    private void createAccount(View v){
        firebaseAuth.createUserWithEmailAndPassword(ed_email.getText().toString().trim(),ed_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    showSnackbar("Account created successfully",v);
                    setDataFirebaseFirestore();
                    setDataFirebaseDatabase();
                }else{
                    progressDialog.dismiss();
                    showSnackbar("The email is already in use",v);
                    clearFields(ed_email);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d("EMAIL CONTRASEÑA CREAR","Error al crear usuario con email y contraseña");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        setResult(RESULT_CANCELED,null);
    }

    private void setDataFirebaseDatabase(){
        User user=new User(
                firebaseAuth.getUid(),
                ed_name.getText().toString().trim(),
                0,
                "",
                "",
                "",
                0,
                true,
                false,
                false,
                0,
                "",
                false,
                new ArrayList<Viaje>(),
                0,
                0,
                0,
                0,
                new ArrayList<User>(),
                new ArrayList<User>(),
                ""
        );

        reference.child("Usuarios").child(firebaseAuth.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setDataFirebaseFirestore();
            }
        });

    }

    private void setDataFirebaseFirestore(){
        User user=new User(
                firebaseAuth.getUid(),
                ed_name.getText().toString().trim(),
                0,
                "",
                "",
                "",
                0,
                true,
                false,
                false,
                0,
                "",
                false,
                new ArrayList<Viaje>(),
                0,
                0,
                0,
                0,
                new ArrayList<User>(),
                new ArrayList<User>(),
                ""
        );

        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        firestore.collection("Usuarios").document(firebaseAuth.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Intent intent=new Intent();
                intent.putExtra("Email",ed_email.getText().toString().trim());
                intent.putExtra("Password",ed_password.getText().toString().trim());
                intent.putExtra("Name",ed_name.getText().toString().trim());
                getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit()
                        .putString("Password",ed_password.getText().toString()).commit();
                setResult(RESULT_OK,intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d("INSERTAR DATOS","Error al insertar los datos");
            }
        });
    }
}