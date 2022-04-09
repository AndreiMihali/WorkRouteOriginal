package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

public class PayMethod extends AppCompatActivity {

    MaterialToolbar toolbarPayMethods;
    TextInputEditText inputName;
    TextInputEditText inputCardNumber;
    TextInputEditText inputCardMonth;
    TextInputEditText inputCardYear;
    TextInputEditText inputCardCVV;
    Button savePayMethod;

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

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(PayMethod.this).start();
        super.onDestroy();
    }

    private void controls() {
        toolbarPayMethods = findViewById(R.id.toolbarPay);
        inputName = findViewById(R.id.inputFullName);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardMonth = findViewById(R.id.inputMonth);
        inputCardYear = findViewById(R.id.inputYear);
        inputCardCVV = findViewById(R.id.inputCVV);
        savePayMethod = findViewById(R.id.buttonSave);
    }


    private void listeners() {

        toolbarPayMethods.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        savePayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputName.getText().toString().trim().equals("")
                    || inputCardNumber.getText().toString().equals("")
                    || inputCardNumber.getText().toString().length()!=16
                    || inputCardMonth.getText().toString().length()!=2
                    || inputCardYear.getText().toString().length()!=2
                    || inputCardCVV.getText().toString().length()!=3
                ) {
                    Toast.makeText(getApplicationContext(),"Error while validating, check all fields", Toast.LENGTH_SHORT).show();
                } else {
                    //AQUI HAY QUE RECOGER LOS CAMPOS PARA MÁS ADELANTE
                    Toast.makeText(getApplicationContext(),"Payment method saved successfully", Toast.LENGTH_SHORT).show();
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clearFields();
                        }
                    },2000);
                }
            }
        });

    }

    private void clearFields() {
        inputName.setText("");
        inputCardNumber.setText("");
        inputCardMonth.setText("");
        inputCardYear.setText("");
        inputCardCVV.setText("");
    }


}