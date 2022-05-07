package com.example.workroute.notifications;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workroute.R;
import com.example.workroute.activitys.MainActivity;
import com.example.workroute.kotlin.adapters.AdapterNotifications;
import com.example.workroute.kotlin.model.NotificationItem;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<NotificationItem> data;
    private AdapterNotifications adapterNotifications = null;
    private ProgressBar progressBar;
    private TextView txt_null;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        new NetworkCallback().enable(this);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_notifications);
        progressBar = findViewById(R.id.progress_circular_notifications);
        txt_null = findViewById(R.id.txt_null_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data = new ArrayList<>();
        initListeners();
        getData();
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

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Notifications");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    NotificationItem not = snap.getValue(NotificationItem.class);
                    not.setId(snap.getKey());
                    data.add(not);
                }
                progressBar.setVisibility(View.GONE);

                if (data.isEmpty()) {
                    txt_null.setVisibility(View.VISIBLE);
                } else {
                    txt_null.setVisibility(View.GONE);
                }

                if (adapterNotifications != null) {
                    adapterNotifications.notifyDataSetChanged();
                } else {
                    adapterNotifications = new AdapterNotifications(data, getApplicationContext());
                    recyclerView.setAdapter(adapterNotifications);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(Notifications.this).start();
        super.onDestroy();
    }
}