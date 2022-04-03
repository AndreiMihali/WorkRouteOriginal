package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;

public class PayMethod extends AppCompatActivity {

    MaterialToolbar toolbarPayMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_method);
        main();
    }

    private void main() {
        controls();
        listeners();
    }



    private void controls() {
        toolbarPayMethods = findViewById(R.id.toolbarPay);
    }


    private void listeners() {
        toolbarPayMethods.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

}