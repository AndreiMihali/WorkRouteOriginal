package com.example.workroute.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workroute.R;
import com.example.workroute.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ConfirmLogin extends AppCompatActivity {

    private static final int GOOGLE_SIGN_IN = 1;
    private MaterialCardView card_email,card_pass;
    private TextView password_for,button_signUp;
    private TextInputEditText ed_email,ed_password;
    private TextInputLayout layout_email,layout_password;
    private MaterialButton buttton_login,button_google;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private String name="";
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
        ed_email.requestFocus();
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        setColorsFocus(card_email,ed_email,layout_email);
        initActivityResultLauncher();
        initListeners();
    }

    private void initActivityResultLauncher() {
        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_OK){
                            Intent intent=result.getData();
                            String email=intent.getStringExtra("Email");
                            String pass=intent.getStringExtra("Password");
                            name=intent.getStringExtra("Name");
                            ed_email.setText(email);
                            ed_password.setText(pass);
                        }
                        ed_email.requestFocus();
                    }
                }
        );
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
                activityResultLauncher.launch(new Intent(ConfirmLogin.this,CreateAccount.class));
            }
        });

        buttton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData(v);
            }
        });

        button_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
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

    //Metodo para comprobar los campos
    private void checkData(View v){
        if(ed_email.getText().toString().equals("")||ed_password.getText().toString().equals("")){
            showSnackbar("Yo must fill all fields ",v);
            clearFields();
        }else{
            checkLoginData(v);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }
    }

    private void checkLoginData(View v) {
        auth.signInWithEmailAndPassword(ed_email.getText().toString().trim(),ed_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    isFirstTime(name);
                }else{
                    progressDialog.dismiss();
                    showSnackbar("Email or password incorrect",v);
                    clearFields();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.d("EMAIL CONTRASEÑA","Fallo al iniciar sesión con email y contraseña "+e);
            }
        });
    }

    //Metodo para mostrar los mensajes de error
    private void showSnackbar(String message,View v){
        Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
    }

    private void clearFields(){
        ed_email.setText("");
        ed_password.setText("");
        ed_email.requestFocus();
    }

    private void showHome(){
        Toast.makeText(getApplicationContext(),"El usuario ya existe",Toast.LENGTH_SHORT).show();
    }

    private void signInGoogle(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("789989127519-t29vd0ruodrb86um9d1lmggdh8h9k83h.apps.googleusercontent.com")
                .requestEmail()
                .build();

        GoogleSignInClient gsc= GoogleSignIn.getClient(getApplicationContext(),gso);
        gsc.signOut();
        startActivityForResult(gsc.getSignInIntent(),GOOGLE_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GOOGLE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken(),account.getDisplayName());
            }catch (ApiException e){
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken,String name) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setDataInFirebase(name);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void setDataInFirebase(String name){
        User user=new User(
                auth.getUid(),
                name,
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
                new ArrayList<>(),
                0
        );

        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        firestore.collection("Usuarios").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean existe=false;
                for (QueryDocumentSnapshot x:queryDocumentSnapshots){
                    if(x.getId().equals(auth.getUid())){
                        existe=true;
                        break;
                    }
                }
                if (existe){
                    isFirstTime(name);
                }else{
                    firestore.collection("Usuarios").document(auth.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            isFirstTime(name);
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("INSERTAR DATOS","Error al insertar los datos");
            }
        });
    }

    private void isFirstTime(String name){
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("Usuarios").document(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                int veces =Integer.parseInt(documentSnapshot.get("vecesConectadas").toString());
                if(veces<=0){
                    Intent intent=new Intent(ConfirmLogin.this,FirstTimeActivity.class);
                    intent.putExtra("Name",name);
                    startActivity(intent);
                }else{
                    showHome();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("INSERTAR DATOS","Error al insertar los datos");
            }
        });
    }
}