package com.example.workroute.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Provider;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final int CODE_UBI = 100;
    private FloatingActionButton button_menu, button_chats, button_profile, button_notifications, button_settings, button_close, button_ubi;
    private Animation open, close, rotateForward, rotateBackWard;
    private GoogleMap map;
    private LatLng ubiActual;
    private SupportMapFragment mapFragment;
    private LocationManager locationManager;
    private boolean isOpen = false;
    private Criteria criteria;
    private String bestProvider;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_WorkRoute);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*****************************************************************************************************************************
        * UTILIZAMOS LA PRIMERA LINEA DE CÓDIGO PARA HACER QUE EL MAPA OCUPE LA PANTALLA ENTERA
        * UTILIZAMOS EL MAP FRAGMENT PARA INICIAR EL MAPA DE GGOGLE. PARA ESO PREVIAMENTE HABREMOS INCLUIDO LA API KEY DEL GGOGLE MAPS
        * QUE SE ENCUENTRA EN LA CARPETA STRINGS
        * UNA VEZ INICIADO EL MAPA INICIAMOS LAS VARIABLES Y COMPROBAMOS QUE SE TENGAN LOS PERMISOS DE UBICACIÓN
        *******************************************************************************************************************************/

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("mapFragment");
        mapFragment.getMapAsync(this::onMapReady);
        main();
        progressDialog.setMessage("Checking your location. This can take a while...");
        progressDialog.setCanceledOnTouchOutside(false);
        checkPermissions();
    }


    private void main() {
        controls();
        listeners();
        getUserData();
    }

    public void onMapReady(GoogleMap googleMap) {

        /******************************************************************************************************************++
         * DESHABILITAMOS EL BOTON DEL COMPAS DE GGOGLE MAPS Y EL BOTON DE UBICACION YA QUE ESE SERA PERSONALIZADO POR NOSTROS
         * TAMBIEN INICIALIZMAOS EL MAPA EN MADRID (ESTO SEGURAMENTE SE INICIE EM LA UBICACION ACTUAL)
         * *****************************************************************************************************************+*/
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setCompassEnabled(false);
        createMarker();
    }

    private void createMarker() {
        LatLng mad = new LatLng(40.42323502898525, -3.7022032760810064);
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mad,12f));
    }


    /******************************************************************************
     * COMPROBAMOS QUE SE TENGAN LSO PERMISOS DE LOCALIZACION YA QUE SON NECESARIOS
     * EN CASO DE NO TENERLOS SE PEDIRAN AL USUARIO
     *****************************************************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_UBI: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    progressDialog.dismiss();
                    Snackbar.make(findViewById(R.id.rela), "Go to settings and enable location permissions", Snackbar.LENGTH_LONG).show();
                }
            }
            return;
        }
    }

    private void checkPermissions(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, CODE_UBI);
        }else{
            getCurrentLocation();
        }
    }

    /************************************************************************************************
     * COMPROBAMOS QUE EL GPS ESTA HABILITADO YA QUE ES NECESARIO PARA PODER IR A LA UBICACION ACTUAL
     * EN CASO DE NO ESTARLO LE MOSTRAMOS UN DIALOG INDICANODLE QUE TIENE QUE ACEPTARLO
     * PARA ACEPTARLO SE LE REDIRIGE A LOS AJUSTES EN LA PESTAÑA GPS PARA PODER ACTIVARLO
     * EN CASO DE NO HACERLO SE LE MOSTRARA DICHO MENSAJE HASTA QUE LO HAGA
     *  ********************************************************************************************/

    private boolean isLocationEnabled(){
        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            return true;
        }else{
            return false;
        }
    }


    /**
     * AÑADIMOS UNA BARRA DE PROGRESO PARA PODER CARGAR EL MAPA Y LA UBICACION BIEN. ASI EVITAMOS ERRORES
     * AL OBTENER LA UBICACION.
     * SE LLAMA SIEMPRE QUE OBTENEMOS LA UBICACION, ES DECIR SIEMPRE QUE ACTIVEMOS EL GPS, O SIEMPRE QUE QUERAMOS OBTENER LA UBICACION
     */
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        progressDialog.show();
        if(isLocationEnabled()){
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));
            locationManager.requestLocationUpdates(bestProvider,1000,0,this);
        }else{
            showDialogMessageGpsEnable();
        }
    }


    /** ESTE MÉTODO HABILITA EL GPS DESDE LOS AJUSTES*/
    private void enableGps() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activityResultLauncher.launch(settingsIntent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void showDialogMessageGpsEnable(){
        progressDialog.dismiss();
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this,R.style.DialogAlert);
        builder.setMessage("To get your current location, you need to enable the GPS. Do you want enable it?");
        builder.setTitle("GPS DISABLED");
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
                progressDialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @SuppressLint("UnsafeOptInUsageError")
    private void controls() {
        addActivityResultLauncher();
        button_menu = findViewById(R.id.buttonMenu);
        button_chats = findViewById(R.id.buttonMessages);
        button_profile = findViewById(R.id.buttonProfile);
        button_notifications = findViewById(R.id.button_notifications);
        button_settings = findViewById(R.id.buttonSettings);
        button_close = findViewById(R.id.buttonSignOut);
        button_ubi=findViewById(R.id.buttonUbi);
        open = AnimationUtils.loadAnimation(this,R.anim.open_menu);
        close = AnimationUtils.loadAnimation(this,R.anim.close_menu);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this,R.style.ProgressDialog);
        /** MÉTODO PARA AÑADIR ICONOS DE NOTIFICACIÓN A LOS BOTONES*/
        button_notifications.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("UnsafeOptInUsageError")
            @Override
            public void onGlobalLayout() {
                BadgeDrawable badgeDrawable = BadgeDrawable.create(MainActivity.this);
                badgeDrawable.setNumber(2);
                //Important to change the position of the Badge
                badgeDrawable.setHorizontalOffset(30);
                badgeDrawable.setVerticalOffset(20);

                BadgeUtils.attachBadgeDrawable(badgeDrawable, button_notifications, null);

                button_notifications.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackWard = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);
    }

    private void addActivityResultLauncher() {
        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_CANCELED){
                            getCurrentLocation();
                        }
                    }
                }
        );
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
                    animateMenu();
                }
            });

          button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Profile.class);
                startActivity(i);
                animateMenu();
            }
        });

         button_ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLocation();
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

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        map.setMyLocationEnabled(true);
        locationManager.removeUpdates(this);
        ubiActual=new LatLng(location.getLatitude(),location.getLongitude());
        progressDialog.dismiss();
    }

    private void moveToLocation() {
       if(isLocationEnabled()){
           map.animateCamera(CameraUpdateFactory.newLatLngZoom(ubiActual,15f),2000,null);
       }else{
           showDialogMessageGpsEnable();
       }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }


    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    private void getUserData(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                firestore.collection("Usuarios").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Companion.user = documentSnapshot.toObject(User.class);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ERROR AL OBTENER LOS DATOS DEL USUARIO","ERROR AL OBTENER LOS DATOS DEL USUARIO "+e);
                    }
                });
            }
        });
    }
}