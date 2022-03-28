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

public class MainActivity extends AppCompatActivity {

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


    }


}