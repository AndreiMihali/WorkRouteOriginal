package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.workroute.R;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;

public class GeneralSettingsActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

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

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(GeneralSettingsActivity.this).start();
        super.onDestroy();
    }
}