package com.example.workroute.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workroute.R;
import com.example.workroute.adapters.CardItemAdapter;
import com.example.workroute.model.CardItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PayMethod extends AppCompatActivity implements CardItemAdapter.ItemClickListener {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private TextView nullMessage;
    private ProgressBar progressBar;
    private CardItemAdapter myAdapter = null;
    private ArrayList<CardItem> data;
    private Button addNewPayMethod;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_method);
        init();
    }

    private void init() {

        toolbar = findViewById(R.id.toolbarPay);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_payMethods);
        nullMessage = findViewById(R.id.txt_null);
        progressBar = findViewById(R.id.loadContent);
        addNewPayMethod = findViewById(R.id.btn_addPayMethod);
        firebaseAuth = FirebaseAuth.getInstance();
        data = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        initListeners();
        if (firebaseAuth.getCurrentUser() != null) {
            getCardData();
        }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                new MaterialAlertDialogBuilder(PayMethod.this, R.style.ThemeOverlay_App_MaterialAlertDialog)
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
                                Toast.makeText(getApplicationContext(), "Action cancelled", Toast.LENGTH_SHORT).show();
                                getCardData();
                            }
                        }).show();
            }
        };

        ItemTouchHelper myItemTouchHelper = new ItemTouchHelper(simpleCallback);
        myItemTouchHelper.attachToRecyclerView(recyclerView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_paymethods, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.helpMenu:
                Toast.makeText(getApplicationContext(), "To delete a payment method, swipe it to the left", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCardData() {
        progressBar.setVisibility(View.VISIBLE);
        data.clear();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                data.clear();
                myAdapter.notifyDataSetChanged();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String cardNumber = snap.child("cardNumber").getValue().toString();
                    String cardName = snap.child("cardName").getValue().toString();
                    String cardActive = snap.child("cardActive").getValue().toString();
                    String cardType = snap.child("cardType").getValue().toString();
                    data.add(new CardItem((cardActive.equals("true")) ? R.drawable.bx_check : R.drawable.bx_x, cardNumber, cardName, (cardType.equals("visa")) ? R.drawable.bxl_visa : R.drawable.bxl_mastercard));
                }
                progressBar.setVisibility(View.GONE);
                if (data.isEmpty()) {
                    nullMessage.setVisibility(View.VISIBLE);
                } else {
                    nullMessage.setVisibility(View.GONE);
                }

                if (myAdapter != null) {
                    myAdapter.notifyDataSetChanged();
                } else {
                    myAdapter = new CardItemAdapter(getApplicationContext(), data);
                    recyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        };

        FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("payMethods").addValueEventListener(postListener);

        myAdapter = new CardItemAdapter(getApplicationContext(), data);
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
                showDialog();
            }
        });
    }

    private void showDialog() {
        FullDialogPayMethod dialogPayMethod = FullDialogPayMethod.newInstance();
        dialogPayMethod.show(getSupportFragmentManager(), "TAG");
    }

    private void deletePayMethod(String number) {

        FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("payMethods")
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