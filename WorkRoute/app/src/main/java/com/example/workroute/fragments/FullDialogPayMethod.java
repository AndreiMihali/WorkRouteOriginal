package com.example.workroute.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.workroute.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FullDialogPayMethod extends DialogFragment {

    private FirebaseAuth firebaseAuth;
    private TextInputEditText inputName;
    private TextInputEditText inputCardNumber;
    private TextInputEditText inputCardMonth;
    private TextInputEditText inputCardYear;
    private TextInputEditText inputCardCVV;
    private ProgressDialog progressDialog;
    private TextInputLayout cardNumber;
    private TextInputLayout cardMonth;
    private TextInputLayout cardYear;

    public static FullDialogPayMethod newInstance() {
        return new FullDialogPayMethod();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFullScreen);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setWindowAnimations(R.style.dialogFullAnimation);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_full_screen_pay_method, container, false);
        progressDialog = new ProgressDialog(view.getContext(), R.style.ProgressDialog);
        progressDialog.setMessage("Please wait while we are saving your pay method");
        progressDialog.setTitle("SAVING");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();
        inputName = view.findViewById(R.id.inputFullName);
        inputCardNumber = view.findViewById(R.id.inputCardNumber);
        inputCardMonth = view.findViewById(R.id.inputMonth);
        inputCardYear = view.findViewById(R.id.inputYear);
        inputCardCVV = view.findViewById(R.id.inputCVV);
        cardNumber = view.findViewById(R.id.textLayoutCardNumber);
        cardMonth = view.findViewById(R.id.textLayoutMonth);
        cardYear = view.findViewById(R.id.textLayoutYear);
        ImageButton close = view.findViewById(R.id.close_fullScreen);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sowMessage(view.getContext());
            }
        });

        MaterialButton buttonSave = view.findViewById(R.id.saveMethod);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(view.getContext());
            }
        });

        inputCardMonth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!inputCardMonth.getText().toString().equals("")) {
                        if (Integer.parseInt(inputCardMonth.getText().toString()) <= 0 || Integer.parseInt(inputCardMonth.getText().toString()) > 12) {
                            cardMonth.setErrorEnabled(true);
                            cardMonth.setError("Invalid month");
                        }
                    }
                } else {
                    cardMonth.setErrorEnabled(false);
                }
            }
        });

        inputCardYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!inputCardYear.getText().toString().equals("")) {
                        if (Integer.parseInt(inputCardYear.getText().toString()) <= 22) {
                            cardYear.setErrorEnabled(true);
                            cardYear.setError("Invalid year");
                        }
                    }
                } else {
                    cardYear.setErrorEnabled(false);
                }
            }
        });

        inputCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (s.toString().startsWith("5")) {
                        cardNumber.setStartIconDrawable(R.drawable.bxl_mastercard);
                    } else if (s.toString().startsWith("4")) {
                        cardNumber.setStartIconDrawable(R.drawable.bxl_visa);
                    }
                } else {
                    cardNumber.setStartIconDrawable(R.drawable.ic_baseline_credit_card_24);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    private void saveData(Context context) {
        progressDialog.show();
        if (inputName.getText().toString().trim().equals("")
                || inputCardNumber.getText().toString().equals("")
                || inputCardNumber.getText().toString().length() != 16
                || inputCardMonth.getText().toString().length() != 2
                || inputCardYear.getText().toString().length() != 2
                || inputCardCVV.getText().toString().length() != 3
                || cardYear.isErrorEnabled()
                || cardMonth.isErrorEnabled()
                || cardNumber.isErrorEnabled()
        ) {
            progressDialog.dismiss();
            Toast.makeText(context, "Error while validating, check all fields", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            addDataFirebase();
        }
    }

    private void clearFields() {
        inputName.setText("");
        inputCardNumber.setText("");
        inputCardMonth.setText("");
        inputCardYear.setText("");
        inputCardCVV.setText("");
        cardMonth.setErrorEnabled(false);
        cardYear.setErrorEnabled(false);
        cardNumber.setErrorEnabled(false);
    }

    private void addDataFirebase() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cardNumber", inputCardNumber.getText().toString());
        map.put("cardName", inputName.getText().toString());
        map.put("cardExpiration", inputCardMonth.getText().toString() + " " + inputCardYear.getText().toString());
        map.put("cardCvv", inputCardCVV.getText().toString());
        map.put("cardActive", "true");
        map.put("cardType", (inputCardNumber.getText().toString().startsWith("5")) ? "mastercard" : "visa");

        FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("payMethods").child(inputCardNumber.getText().toString().replaceFirst("[0-9]{12}", "XXXX XXXX XXXX ")).setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        clearFields();
                        dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.e("ERROR AL INSERTAR LOS METODOS DE PAGO", e.getMessage());
                        progressDialog.dismiss();
                    }
                });
    }


    private void sowMessage(Context context) {
        new MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle("CAUTION")
                .setCancelable(false)
                .setMessage("If you exit your data will be lose")
                .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
