package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.workroute.R;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;

public class HelpCenter extends AppCompatActivity {

    /*
        floating button menu
        ubication button
        driver map
        customer map
        chat
        profile
        ----
        personal information
        pay method
        active subscriptions
        general settings
        delete account
        log out
        ----
        notifications

     */


    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        new NetworkCallback().enable(this);
        main();
    }

    private void main() {
        controls();
        initListeners();
    }

    private void controls() {
        toolbar = findViewById(R.id.toolbarUseWorkRoute);
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }


}
