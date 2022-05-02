package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.workroute.R;
import com.example.workroute.driverActivities.DrivingLicense;
import com.google.android.material.appbar.MaterialToolbar;

public class ActiveSubscriptions extends AppCompatActivity {

    private MaterialToolbar toolbarSubs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_subscriptions);
        main();

    }

    private void main() {
        controls();
        listeners();
    }
        private void controls() {
            toolbarSubs = findViewById(R.id.toolbar_subs);
        }

        private void listeners() {

            toolbarSubs.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                    finish();
                }
            });


        }



    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(ActiveSubscriptions.this).start();
        super.onDestroy();
    }
}
