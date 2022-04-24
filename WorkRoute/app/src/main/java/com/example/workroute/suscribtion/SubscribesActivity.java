package com.example.workroute.suscribtion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.workroute.R;
import com.example.workroute.model.SubscriptionItem;
import com.example.workroute.network.callback.NetworkCallback;
import com.example.workroute.suscribtion.adapter.SubscriptionAdapter;

import java.util.ArrayList;

public class SubscribesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubscriptionAdapter adapter;
    private ArrayList<SubscriptionItem> data;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Subscribe);
        super.onCreate(savedInstanceState);
        new NetworkCallback().enable(this);
        setContentView(R.layout.layout_suscripciones);
        init();
    }

    private void init(){
        recyclerView=findViewById(R.id.recycler_subs);
        data=new ArrayList<>();
        toolbar=findViewById(R.id.toolbar);
        initData();
        initListeners();
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

    private void initData(){
        data.add(new SubscriptionItem("$10","STARTER","1 drive request per day","#004ba0"));
        data.add(new SubscriptionItem("$20","BASIC","2 drive request per day","#119608"));
        data.add(new SubscriptionItem("$30","PREMIUM","Unlimited drive request per day","#A5AD22"));
        data.add(new SubscriptionItem("$40","PRO","unlimited request per day","#AD2237"));
        adapter=new SubscriptionAdapter(data,getApplicationContext());
        GridLayoutManager glm=new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(adapter);
    }
}