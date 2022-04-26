package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.workroute.R;
import com.example.workroute.profile.Profile;
import com.example.workroute.suscribtion.SubscribesActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Personal_information extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RelativeLayout relativeChangeColors;
    private ImageView img_lockChange;
    private TextView tv_unlock_save,tv_user_mail;
    private boolean sw = false;
    private TextInputEditText ed_name,ed_password;
    private LinearLayout ln_subscription;

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
        initData();
    }

    private void initData() {

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
                } else if(sw==true) {
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

        ln_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(Personal_information.this, SubscribesActivity.class));
            }
        });


    }

}
