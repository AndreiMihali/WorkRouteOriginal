package com.example.workroute.driverActivities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDriverInformation extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteColors,autoCompleteCars;
    private ArrayAdapter<String> adapterColors;
    private ArrayAdapter<String> adapterTypes;
    private String colors[]={"Red","Blue","Black","White","Gray","Green","Yellow","Brown","Pink"};
    private String types[]={"HatchBack","Sedan","Limousine","Cabriolet","Minibus","Minivan","Crossover","Jeep","Pickup"};
    private TextInputEditText documentId,plateNumber;
    private MaterialButton btnContinue;
    private ProgressDialog progressDialog;
    private HashMap<String,Object> hourDepartureHome=new HashMap<>();
    private HashMap<String,Object> hourDepartureWork=new HashMap<>();

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
        documentId=findViewById(R.id.ed_idNumber);
        plateNumber=findViewById(R.id.ed_plate);
        autoCompleteCars.setAdapter(adapterTypes);
        autoCompleteColors.setAdapter(adapterColors);
        btnContinue=findViewById(R.id.btn_continue);
        progressDialog=new ProgressDialog(this,R.style.ProgressDialog);
        progressDialog.setMessage("Please wait...");
        initListeners();
    }

    private void initListeners() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAllFieldsOk()){
                    showMaterialTimePicker("Departure time of your home",7,"home");
                }else{
                    Snackbar.make(v,"You must need fill all fields",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showMaterialTimePicker(String title,int hour,String placeDeparture){
        int timeFormat=(DateFormat.is24HourFormat(this))?TimeFormat.CLOCK_24H:TimeFormat.CLOCK_12H;
        MaterialTimePicker builder=new MaterialTimePicker.Builder()
                .setTimeFormat(timeFormat)
                .setHour(hour)
                .setTheme(R.style.ThemeOverlay_App_TimePicker)
                .setMinute(0)
                .setTitleText(title)
                .build();

        builder.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(placeDeparture.equals("home")){
                    hourDepartureHome.put("hour",String.valueOf(builder.getHour()));
                    hourDepartureHome.put("minute",String.valueOf(builder.getMinute()));
                    showMaterialTimePicker("Departure time of your work",15,"work");
                }else{
                    hourDepartureWork.put("hour",String.valueOf(builder.getHour()));
                    hourDepartureWork.put("minute",String.valueOf(builder.getMinute()));
                    progressDialog.show();
                    setDataInDatabase();
                }
            }
        });

        builder.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        builder.show(getSupportFragmentManager(),"A");

    }

    private boolean isAllFieldsOk(){
        if(autoCompleteColors.getText().toString().isEmpty()
            ||autoCompleteCars.getText().toString().isEmpty()
            ||plateNumber.getText().toString().isEmpty()
            ||documentId.getText().toString().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    private void setDataInDatabase(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                HashMap<String,Object> hashMap2=new HashMap<>();
                hashMap2.put("DocumentId",documentId.getText().toString().trim());
                hashMap2.put("PlateNumber",plateNumber.getText().toString().trim());
                hashMap2.put("CarType",autoCompleteCars.getText().toString().trim());
                hashMap2.put("CarColor",autoCompleteColors.getText().toString().trim());
                hashMap2.put("hourDepartureHome",hourDepartureHome);
                hashMap2.put("hourDepartureWork",hourDepartureWork);
                FirebaseDatabase.getInstance().getReference().child("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("DriverInformation").setValue(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("conductor","true");
                        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                HashMap<String,Object> hashMap=new HashMap<>();
                                hashMap.put("conductor",true);
                                FirebaseFirestore.getInstance().collection("Usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        progressDialog.dismiss();
                                        Companion.user.setConductor(true);
                                        startActivity(new Intent(AddDriverInformation.this, DriverMap.class));
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

}