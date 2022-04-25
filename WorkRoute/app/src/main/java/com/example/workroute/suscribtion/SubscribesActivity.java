package com.example.workroute.suscribtion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.workroute.R;
import com.example.workroute.activitys.CustomerMap;
import com.example.workroute.model.SubscriptionItem;
import com.example.workroute.network.callback.NetworkCallback;
import com.example.workroute.suscribtion.adapter.SubscriptionAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SubscribesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SubscriptionAdapter adapter;
    private ArrayList<SubscriptionItem> data;
    private Toolbar toolbar;
    private MaterialButton btn_skip,btn_subscribe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_EditProfile);
        super.onCreate(savedInstanceState);
        new NetworkCallback().enable(this);
        setContentView(R.layout.layout_suscripciones);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        init();
    }

    private void init(){
        recyclerView=findViewById(R.id.recycler_subs);
        data=new ArrayList<>();
        toolbar=findViewById(R.id.toolbar);
        btn_skip=findViewById(R.id.button_skip);
        btn_subscribe=findViewById(R.id.button_subscribe);
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

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubscribesActivity.this,CustomerMap.class));
                finish();
            }
        });

        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getItemSelectedIndex()<0){
                    Snackbar.make(v,"You must need to select one subscription first",Snackbar.LENGTH_LONG).show();
                }
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