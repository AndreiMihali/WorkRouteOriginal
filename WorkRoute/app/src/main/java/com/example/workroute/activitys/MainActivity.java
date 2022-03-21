package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.workroute.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton button_menu,button_messages,button_profile,button_help,button_about_us,button_close;
    Animation open,close,rotateForward,rotateBackWard;
    GoogleMap map;
    boolean isOpen = false;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);
        main();

    }

    private void main() {
        controls();
        listeners();


    }

    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }

    private void controls() {
        button_menu = findViewById(R.id.buttonMenu);
        button_messages = findViewById(R.id.buttonMessages);
        button_profile = findViewById(R.id.buttonProfile);
        button_help = findViewById(R.id.buttonHelp);
        button_about_us = findViewById(R.id.buttonAboutUs);
        button_close = findViewById(R.id.buttonSignOut);


        open = AnimationUtils.loadAnimation(this,R.anim.open_menu);
        close = AnimationUtils.loadAnimation(this,R.anim.close_menu);
        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackWard = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);
    }

    private void listeners() {
            button_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateMenu();
                }
            });

        button_messages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Saludos del menu 22222", Toast.LENGTH_SHORT).show();
                }
            });

        //TODO : FALTAN LOS LISTENERS DEL RESTO DE BOTONES


        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"Saludos del menu 333", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void animateMenu() {
        if (isOpen) {
            button_menu.startAnimation(rotateForward);

            button_messages.startAnimation(close);
            button_profile.startAnimation(close);
            button_help.startAnimation(close);
            button_about_us.startAnimation(close);
            button_close.startAnimation(close);

            button_messages.setVisibility(View.INVISIBLE);
            button_profile.setVisibility(View.INVISIBLE);
            button_help.setVisibility(View.INVISIBLE);
            button_about_us.setVisibility(View.INVISIBLE);
            button_close.setVisibility(View.INVISIBLE);

            button_messages.setClickable(false);
            button_profile.setClickable(false);
            button_help.setClickable(false);
            button_about_us.setClickable(false);
            button_close.setClickable(false);

            isOpen=false;
        } else {
            button_menu.startAnimation(rotateBackWard);

            button_messages.startAnimation(open);
            button_profile.startAnimation(open);
            button_help.startAnimation(open);
            button_about_us.startAnimation(open);
            button_close.startAnimation(open);


            button_messages.setVisibility(View.VISIBLE);
            button_profile.setVisibility(View.VISIBLE);
            button_help.setVisibility(View.VISIBLE);
            button_about_us.setVisibility(View.VISIBLE);
            button_close.setVisibility(View.VISIBLE);

            button_messages.setClickable(true);
            button_profile.setClickable(true);
            button_help.setClickable(true);
            button_about_us.setClickable(true);
            button_close.setClickable(true);
            isOpen=true;
        }
    }
}