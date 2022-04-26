package com.example.workroute.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.driverActivities.DrivingLicense;
import com.example.workroute.activitys.GeneralSettingsActivity;
import com.example.workroute.initActivities.LoginActivity;
import com.example.workroute.activitys.MainActivity;
import com.example.workroute.activitys.PayMethod;
import com.example.workroute.activitys.Personal_information;
import com.example.workroute.companion.Companion;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private LinearLayout ln_profile,ln_pay,ln_license,ln_settings,ln_delete,ln_logout;
    private TextView txt_username;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        setTitle(null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        new NetworkCallback().enable(this);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(uiOptions);
        init();

    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editProfile :
        }
        return super.onOptionsItemSelected(item);
    }
     */

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(Profile.this).start();
        super.onDestroy();
    }


    private void init(){
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth=FirebaseAuth.getInstance();
        ln_profile=findViewById(R.id.personal_information);
        ln_pay=findViewById(R.id.payMethod);
        ln_license=findViewById(R.id.driveLicense);
        ln_settings=findViewById(R.id.settings);
        ln_delete=findViewById(R.id.deleteAccount);
        ln_logout=findViewById(R.id.logout);
        profileImage=findViewById(R.id.profileImage);
        initListeners();
        setData();
    }

    private void setData() {
        if(Companion.user!=null){
            //txt_username.setText(Companion.user.getNombre());

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

        ln_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Personal_information.class));
            }
        });

        ln_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, PayMethod.class));
            }
        });

        ln_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, DrivingLicense.class));
            }
        });

        ln_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, GeneralSettingsActivity.class));
            }
        });

        ln_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                new MainActivity.Destroy(Profile.this);
                startActivity(new Intent(Profile.this, LoginActivity.class));
                finish();
            }
        });
    }
}