package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;

public class DrivingLicense extends AppCompatActivity {
    MaterialToolbar toolbarDrivingLicense;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license);
        main();
    }

    private void main() {
        controls();
        listeners();
    }



    private void controls() {
        toolbarDrivingLicense = findViewById(R.id.toolbar);
    }


    private void listeners() {
        toolbarDrivingLicense.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

}