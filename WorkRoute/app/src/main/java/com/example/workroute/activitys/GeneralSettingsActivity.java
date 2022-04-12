package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.workroute.R;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;

public class GeneralSettingsActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private CheckBox emailBox;
    private CheckBox appBox;

    private RadioButton mapView1;
    private RadioButton mapView2;
    private RadioGroup myGroup;



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
        mapView1 = findViewById(R.id.radioMapView1);
        mapView2 = findViewById(R.id.radioMapView2);
        myGroup = findViewById(R.id.radioGroupMapViews);


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
                    //TODO: CODIGO QUE SE QUIERA METER
                    Toast.makeText(getApplicationContext(),"app siii",Toast.LENGTH_SHORT).show();

                } else {
                    //TODO: CODIGO QUE SE QUIERA METER
                    Toast.makeText(getApplicationContext(),"app nooo",Toast.LENGTH_SHORT).show();

                }

            }
        });

        myGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            if (mapView1.isChecked()) {
                //TODO: CODIGO QUE SE QUIERA METER PARA EL CAMBIO DE MAPA O LO QUE SEA
                Toast.makeText(getApplicationContext(),"view1",Toast.LENGTH_SHORT).show();
            }

            if (mapView2.isChecked()) {
                //TODO: CODIGO QUE SE QUIERA METER PARA EL CAMBIO DE MAPA O LO QUE SEA
                Toast.makeText(getApplicationContext(),"view2",Toast.LENGTH_SHORT).show();
            }

            }
        });



    }

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(GeneralSettingsActivity.this).start();
        super.onDestroy();
    }
}