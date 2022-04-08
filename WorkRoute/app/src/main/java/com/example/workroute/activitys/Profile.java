package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    private TextView txt_viewAsDriver;
    private MaterialToolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private LinearLayout ln_profile,ln_pay,ln_license,ln_settings,ln_delete,ln_logout;
    private TextView txt_username;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        new NetworkCallback().enable(this);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(uiOptions);
        init();
    }

    private void init(){
        toolbar=findViewById(R.id.toolbar);
        txt_viewAsDriver=findViewById(R.id.txt_view_asDriver);
        firebaseAuth=FirebaseAuth.getInstance();
        ln_profile=findViewById(R.id.profile);
        ln_pay=findViewById(R.id.payMethod);
        ln_license=findViewById(R.id.driveLicense);
        ln_settings=findViewById(R.id.settings);
        ln_delete=findViewById(R.id.deleteAccount);
        ln_logout=findViewById(R.id.logout);
        txt_username=findViewById(R.id.username);
        profileImage=findViewById(R.id.profileImage);
        initListeners();
        setData();
    }

    private void setData() {
        if(Companion.user!=null){
            txt_username.setText(Companion.user.getNombre());

            if(Companion.user.getFotoPerfil().equals("")){
                profileImage.setImageDrawable(getDrawable(R.drawable.default_user_login));
            }else{
                Glide.with(getApplicationContext()).load(Companion.user.getFotoPerfil()).into(profileImage);
            }
        }
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        ln_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,PayMethod.class));
            }
        });

        ln_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,DrivingLicense.class));
            }
        });

        ln_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(Profile.this,LoginActivity.class));
                finish();
            }
        });
    }
}