package com.example.workroute.driverActivities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

public class AddDriverInformation extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteColors,autoCompleteCars;
    private ArrayAdapter<String> adapterColors;
    private ArrayAdapter<String> adapterTypes;
    private String colors[]={"Red","Blue","Black","White","Gray","Green","Yellow","Brown","Pink"};
    private String types[]={"HatchBack","Sedan","Limousine","Cabriolet","Minibus","Minivan","Crossover","Jeep","Pickup"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.backDriverInformation);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver_information);
        init();
    }

    private void init(){
        autoCompleteCars=findViewById(R.id.autocompleteTypes);
        autoCompleteColors=findViewById(R.id.autocompleteColors);
        adapterColors=new ArrayAdapter<String>(this,R.layout.list_item,colors);
        adapterTypes=new ArrayAdapter<String>(this,R.layout.list_item,types);

        autoCompleteCars.setAdapter(adapterTypes);
        autoCompleteColors.setAdapter(adapterColors);

        initListeners();
    }

    private void initListeners() {

    }

}