package com.example.workroute.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workroute.R;
import com.example.workroute.adapters.CardItemAdapter;
import com.example.workroute.model.CardItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PayMethod extends AppCompatActivity implements CardItemAdapter.ItemClickListener{

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private TextView nullMessage;
    private ProgressBar progressBar;
    private CardItemAdapter myAdapter=null;
    private ArrayList<CardItem> data;
    private Button addNewPayMethod;
    private FirebaseAuth firebaseAuth;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextInputEditText inputName;
    private TextInputEditText inputCardNumber;
    private TextInputEditText inputCardMonth;
    private TextInputEditText inputCardYear;
    private TextInputEditText inputCardCVV;
    private Button savePayMethod;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_method);
        init();
    }

    private void init(){

        toolbar=findViewById(R.id.toolbarPay);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recycler_payMethods);
        nullMessage=findViewById(R.id.txt_null);
        progressBar=findViewById(R.id.loadContent);
        addNewPayMethod=findViewById(R.id.btn_addPayMethod);
        firebaseAuth=FirebaseAuth.getInstance();
        bottomSheetBehavior=BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_payMethod));
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        data=new ArrayList<>();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        initListeners();
        if(firebaseAuth.getCurrentUser()!=null){
            getCardData();
        }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new MaterialAlertDialogBuilder(PayMethod.this,R.style.DialogAlert)
                        .setTitle("CAUTION")
                        .setCancelable(false)
                        .setMessage("Are you sure do you want to delete this payment method?")
                        .setIcon(getDrawable(R.drawable.ic_outline_delete_forever_24))
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePayMethod(data.get(myAdapter.itemIndexSelected).getNumberCard());
                                data.remove(viewHolder.getAdapterPosition());
                                myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"Action cancelled",Toast.LENGTH_SHORT).show();
                                getCardData();
                            }
                        }).show();


                /*
                deletePayMethod(data.get(myAdapter.itemIndexSelected).getNumberCard());
                data.remove(viewHolder.getAdapterPosition());
                myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                 */

            }
        };

        ItemTouchHelper myItemTouchHelper = new ItemTouchHelper(simpleCallback);
        myItemTouchHelper.attachToRecyclerView(recyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.helpMenu :
                Toast.makeText(getApplicationContext(),"To delete a payment method, swipe it to the left",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCardData() {
        progressBar.setVisibility(View.VISIBLE);
        data.clear();

        ValueEventListener postListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                data.clear();
                myAdapter.notifyDataSetChanged();
                for(DataSnapshot snap:snapshot.getChildren()){
                    String cardNumber=snap.child("cardNumber").getValue().toString();
                    String cardName=snap.child("cardName").getValue().toString();
                    String cardActive=snap.child("cardActive").getValue().toString();
                    String cardType=snap.child("cardType").getValue().toString();
                    data.add(new CardItem((cardActive.equals("true"))?R.drawable.bx_check:R.drawable.bx_x,cardNumber,cardName,(cardType.equals("visa"))?R.drawable.bxl_visa:R.drawable.bxl_mastercard));
                }
                progressBar.setVisibility(View.GONE);
                if(data.isEmpty()){
                    nullMessage.setVisibility(View.VISIBLE);
                }else{
                    nullMessage.setVisibility(View.GONE);
                }

                if(myAdapter!=null){
                    myAdapter.notifyDataSetChanged();
                }else{
                    myAdapter=new CardItemAdapter(getApplicationContext(),data);
                    recyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        };

        FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getUid()).child("payMethods").addValueEventListener(postListener);

        myAdapter=new CardItemAdapter(getApplicationContext(),data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(PayMethod.this).start();
        super.onDestroy();
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        addNewPayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
    }

    private void showBottomSheet(){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setSaveFlags(BottomSheetBehavior.SAVE_ALL);
        inputName = findViewById(R.id.inputFullName);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardMonth = findViewById(R.id.inputMonth);
        inputCardYear = findViewById(R.id.inputYear);
        inputCardCVV = findViewById(R.id.inputCVV);
        savePayMethod = findViewById(R.id.buttonSave);
        TextInputLayout cardNumber=findViewById(R.id.textLayoutCardNumber);

        savePayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                if (inputName.getText().toString().trim().equals("")
                        || inputCardNumber.getText().toString().equals("")
                        || inputCardNumber.getText().toString().length()!=16
                        || inputCardMonth.getText().toString().length()!=2
                        || inputCardYear.getText().toString().length()!=2
                        || inputCardCVV.getText().toString().length()!=3
                ) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext().getApplicationContext(),"Error while validating, check all fields", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    addDataFirebase();
                }
            }
        });

        inputCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0) {
                    if(s.toString().startsWith("5")){
                        cardNumber.setStartIconDrawable(R.drawable.bxl_mastercard);
                    }else if(s.toString().startsWith("4")){
                        cardNumber.setStartIconDrawable(R.drawable.bxl_visa);
                    }
                }else{
                    cardNumber.setStartIconDrawable(R.drawable.ic_baseline_credit_card_24);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    private void addDataFirebase(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("cardNumber",inputCardNumber.getText().toString());
        map.put("cardName",inputName.getText().toString());
        map.put("cardExpiration",inputCardMonth.getText().toString()+" "+inputCardYear.getText().toString());
        map.put("cardCvv",inputCardCVV.getText().toString());
        map.put("cardActive","true");
        map.put("cardType",(inputCardNumber.getText().toString().startsWith("5"))?"mastercard":"visa");

        FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getUid()).child("payMethods").child(inputCardNumber.getText().toString().replaceFirst("[0-9]{12}","XXXX XXXX XXXX ")).setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        bottomSheetBehavior.setSaveFlags(BottomSheetBehavior.SAVE_ALL);
                        clearFields();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.e("ERROR AL INSERTAR LOS METODOS DE PAGO",e.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void deletePayMethod(String number){

            FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getUid()).child("payMethods")
                    .child(number).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    data.remove(number);
                }
            });

    }

    @Override
    public void onItemClick(View view) {

    }
}