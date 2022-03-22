package com.example.workroute.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.workroute.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_UBI = 100;
    FloatingActionButton button_menu, button_chats, button_profile, button_notifications, button_settings, button_close, button_ubi;
    Animation open, close, rotateForward, rotateBackWard;
    GoogleMap map;
    LatLng ubiActual;
    SupportMapFragment mapFragment;
    LocationManager locationManager;
    boolean isOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_WorkRoute);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("mapFragment");
        mapFragment.getMapAsync(this::onMapReady);
        main();

    }


    private void main() {
        controls();
        listeners();
    }

    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setCompassEnabled(false);
        createMarker();
        getUbiActual();
    }

    private void createMarker() {
        LatLng mad = new LatLng(40.42323502898525, -3.7022032760810064);
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(mad, 12f), 2000, null);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_UBI: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getUbiActual();
                } else {
                    Snackbar.make(findViewById(R.id.rela), "Go to settings and enable ubication permissions", Snackbar.LENGTH_LONG).show();
                }
            }
            return;
        }
    }


    private void enableGps() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }


    private LatLng getUbiActual() {
        locationManager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);
        final boolean gbsEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, CODE_UBI);
            return null;
        }
        map.setMyLocationEnabled(true);
        if (!gbsEnabled) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setMessage("You must enable the GPS. Do you want enable it?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    enableGps();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            ubiActual = new LatLng(location.getLatitude(), location.getLongitude());
        }
        return ubiActual;
    }

    private void moveToActualUbication(){
        if(ubiActual!=null){
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(getUbiActual(),15f),2000,null);
        }
    }

    private void controls() {
        button_menu = findViewById(R.id.buttonMenu);
        button_chats = findViewById(R.id.buttonMessages);
        button_profile = findViewById(R.id.buttonProfile);
        button_notifications = findViewById(R.id.button_notifications);
        button_settings = findViewById(R.id.buttonSettings);
        button_close = findViewById(R.id.buttonSignOut);
        button_ubi=findViewById(R.id.buttonUbi);

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

            button_chats.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, Chats.class);
                    startActivity(i);
                    //animateMenu();
                }
            });

          button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Profile.class);
                startActivity(i);
                //animateMenu();
            }
        });

         button_ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToActualUbication();
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
            button_menu.setImageResource(R.drawable.ic_baseline_menu_24);


            button_chats.startAnimation(close);
            button_profile.startAnimation(close);
            button_notifications.startAnimation(close);
            button_settings.startAnimation(close);
            button_close.startAnimation(close);

            button_chats.setVisibility(View.INVISIBLE);
            button_profile.setVisibility(View.INVISIBLE);
            button_notifications.setVisibility(View.INVISIBLE);
            button_settings.setVisibility(View.INVISIBLE);
            button_close.setVisibility(View.INVISIBLE);

            button_chats.setClickable(false);
            button_profile.setClickable(false);
            button_notifications.setClickable(false);
            button_settings.setClickable(false);
            button_close.setClickable(false);

            isOpen=false;
        } else {
            button_menu.startAnimation(rotateBackWard);
            button_menu.setImageResource(R.drawable.ic_baseline_menu_open_24);
            button_chats.startAnimation(open);
            button_profile.startAnimation(open);
            button_notifications.startAnimation(open);
            button_settings.startAnimation(open);
            button_close.startAnimation(open);


            button_chats.setVisibility(View.VISIBLE);
            button_profile.setVisibility(View.VISIBLE);
            button_notifications.setVisibility(View.VISIBLE);
            button_settings.setVisibility(View.VISIBLE);
            button_close.setVisibility(View.VISIBLE);

            button_chats.setClickable(true);
            button_profile.setClickable(true);
            button_notifications.setClickable(true);
            button_settings.setClickable(true);
            button_close.setClickable(true);
            isOpen=true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.clear();
        mapFragment.onDestroy();
    }
}