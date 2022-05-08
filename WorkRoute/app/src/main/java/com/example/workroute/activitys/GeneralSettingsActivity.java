package com.example.workroute.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workroute.R;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;

public class GeneralSettingsActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private CheckBox emailBox;
    private CheckBox appBox;
    private TextView tv_HowToUseWorkRoute;
    private TextView tv_AboutUs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);
        new NetworkCallback().enable(this);
        main();
    }

    private void main() {
        controls();
        initListeners();
    }

    private void controls() {
        toolbar = findViewById(R.id.toolbarSettings);
        emailBox = findViewById(R.id.checkBoxEmailNotifications);
        appBox = findViewById(R.id.checkBoxAppNotifications);
        tv_HowToUseWorkRoute = findViewById(R.id.tvHowToUseWorkRoute);
        tv_AboutUs = findViewById(R.id.tvAboutUs);


    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        emailBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (emailBox.isChecked()) {
                    //TODO: CODIGO QUE SE QUIERA METER
                    Toast.makeText(getApplicationContext(),"email siii",Toast.LENGTH_SHORT).show();

                } else {
                    //TODO: CODIGO QUE SE QUIERA METER
                    Toast.makeText(getApplicationContext(),"email nooo",Toast.LENGTH_SHORT).show();

                }

            }
        });

        appBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (appBox.isChecked()) {
                    getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit()
                            .putBoolean("notificationsApp",true).commit();

                } else {
                    getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit()
                            .putBoolean("notificationsApp",false).commit();

                }

            }
        });

        tv_HowToUseWorkRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralSettingsActivity.this, HelpCenter.class));
            }
        });

        tv_AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralSettingsActivity.this,AppInformation.class));
            }
        });


    }

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(GeneralSettingsActivity.this).start();
        super.onDestroy();
    }
}