package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.workroute.R;
import com.example.workroute.adapters.ActiveSubAdapter;
import com.example.workroute.driverActivities.DrivingLicense;
import com.example.workroute.model.SubscribedUser;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class ActiveSubscriptions extends AppCompatActivity {

    private MaterialToolbar toolbarSubs;
    private ArrayList <SubscribedUser> elementsRecycler = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_subscriptions);
        main();

    }

    private void main() {
         initRecycler();
         controls();
         listeners();
         getData();
    }



    private void controls() {
        toolbarSubs = findViewById(R.id.toolbar_subs);


    }

    private void initRecycler() {

        ActiveSubAdapter myAdapter = new ActiveSubAdapter(elementsRecycler,this);
        RecyclerView recycler = findViewById(R.id.recyclerSubs);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(myAdapter);

    }

    private void getData() {

        elementsRecycler.add(new SubscribedUser(R.drawable.reversocarnet,"Ruben","Via Lusitana 56",R.drawable.carnetfrente));

    }

        private void listeners() {

            toolbarSubs.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                    finish();
                }
            });


        }

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(ActiveSubscriptions.this).start();
        super.onDestroy();
    }
}
