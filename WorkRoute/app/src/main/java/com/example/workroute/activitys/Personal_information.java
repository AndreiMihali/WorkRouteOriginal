package com.example.workroute.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.profile.Profile;
import com.example.workroute.suscribtion.SubscribesActivity;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Personal_information extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RelativeLayout relativeChangeColors;
    private ImageView img_lockChange,imageProfile;
    private TextView tv_unlock_save,tv_user_mail,tv_actualSubscription;
    private boolean sw = false;
    private TextInputEditText ed_name,ed_password;
    private TextInputLayout layout_ed_name,layout_ed_pass;
    private LinearLayout ln_subscription;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_EditProfile);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        main();
        setData();
    }

    private void setData() {
        tv_user_mail.setText(firebaseAuth.getCurrentUser().getEmail().toString());
        if(Companion.user.getFotoPerfil().equals(" ")){
            imageProfile.setImageResource(R.drawable.default_user_login);
        }else{
            Glide.with(this).load(Companion.user.getFotoPerfil()).into(imageProfile);
        }
        ed_name.setText(Companion.user.getNombre());

        tv_actualSubscription.setText(String.valueOf(Companion.user.getNivelSuscripcion()));

        /**
         * HAY QUE AVISAR QUE LA CONTRA DE GOOGLE NO PUEDE CAMBIARSE
         * SI LO QUE HAY EN sp.getString("Password"," ") DEVUELVE UNA CONTRASEÑA NO ES GOOGLE,
         * Tambien se puede pedir que introduzca la antigua contraseña.
         * Pero en caso de que quiera recuperarla porque se le olvido mejor no pedirla xd
         * EN CASO CONTRARIO ES DE GGOGLE
         */
        ed_password.setText(sp.getString("Password",""));

    }

    /**
     * DEJO HECHO EL METODO DE CAMBIO DE CONTRASEÑA PARA QUE LO TENGAS
     */

    private void changePassword(){
        progressDialog.show();
        String password=sp.getString("Password","");
        AuthCredential credential= EmailAuthProvider.getCredential(firebaseAuth.getCurrentUser().getEmail().toString(),password);
        firebaseAuth.getCurrentUser().reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.setTitle("Updating password");
                progressDialog.setMessage("Please wait...");
                //Aqui hay que meter la nueva contraseña que introduzca el usuario
                firebaseAuth.getCurrentUser().updatePassword(ed_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Snackbar.make(findViewById(R.id.rel_layout_general),"The password was changed successfully",Snackbar.LENGTH_SHORT).show();
                        sp.edit().putString("Password",ed_password.getText().toString()).commit();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("PASSWORD UPDATE","ERROR WHILE CHANGING THE PASSWORD"+e);
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("REAUTHENTICATE ERROR","ERROR WHILE REAUTHENTICATE"+e);
                progressDialog.dismiss();
            }
        });
    }

    private void main() {
        controls();
        initListeners();
    }

    private void controls() {
        toolbar = findViewById(R.id.toolbarInformation);
        relativeChangeColors = findViewById(R.id.relativeChangingColorOnClick);
        tv_unlock_save = findViewById(R.id.textUnlock_Save);
        img_lockChange = findViewById(R.id.imageLock);
        ed_name = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_pass);
        ln_subscription = findViewById(R.id.layout_subscriptionLevel);
        tv_user_mail = findViewById(R.id.tv_userMail);
        firebaseAuth=FirebaseAuth.getInstance();
        imageProfile=findViewById(R.id.profileImage);
        tv_actualSubscription=findViewById(R.id.tv_variableActualSubscription);
        sp=getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        progressDialog=new ProgressDialog(this,R.style.ProgressDialog);
        layout_ed_name=findViewById(R.id.layout_name);
        layout_ed_pass=findViewById(R.id.layout_pass);
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        relativeChangeColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sw==false) {
                    sw=true;
                    relativeChangeColors.setBackgroundColor(getColor(R.color.verde_flojo));
                    img_lockChange.setImageResource(R.drawable.ic_bx_lock_open);
                    tv_unlock_save.setText("SAVE CHANGES");
                    ed_name.setEnabled(true);
                    ed_password.setEnabled(true);
                } else if(sw) {
                    sw=false;
                    relativeChangeColors.setBackgroundColor(Color.parseColor("#DC1C1C"));
                    img_lockChange.setImageResource(R.drawable.ic_baseline_lock_24);
                    tv_unlock_save.setText("UNLOCK FIELDS");
                    ed_name.setEnabled(false);
                    ed_password.setEnabled(false);
                    //llamar a un metodo o algo de guardado de datos
                }

            }
        });

        ed_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(ed_password,layout_ed_pass);
                }else{
                    removeColorsFocus(ed_password,layout_ed_pass);
                }
            }
        });

        ed_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(ed_name,layout_ed_name);
                }else{
                    removeColorsFocus(ed_name,layout_ed_name);
                }
            }
        });

        ln_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(Personal_information.this, SubscribesActivity.class));
            }
        });


    }

    private void setColorsFocus(TextInputEditText ed, TextInputLayout txt){
        txt.setHintTextColor(ColorStateList.valueOf(getColor(R.color.secondary)));
        txt.setStartIconTintList(ColorStateList.valueOf(getColor(R.color.secondary)));
        ed.setTextColor(getColor(R.color.secondary));
        txt.setEndIconTintList(ColorStateList.valueOf(getColor(R.color.secondary)));
    }

    private void removeColorsFocus(TextInputEditText ed,TextInputLayout txt){
        ed.setTextColor(Color.parseColor("#BABABA"));
        txt.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
        txt.setStartIconTintList(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
        txt.setEndIconTintList(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
    }

}
