package com.example.workroute.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.Handshake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    private FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    private static final int CODE_UBI = 100;
    Handler iniciarApp;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Splash);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iniciarApp = new Handler();
        checkPermissions();
        addActivityResultLauncher();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_UBI: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    iniciarApp.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(user!=null){
                                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            }else{
                                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                            }
                            finish();
                        }
                    },2000);
                } else {



                            Snackbar.make(findViewById(R.id.rela), "GO TO LOCATION SETTINGS", Snackbar.LENGTH_LONG).
                                    setAction("GO SETTINGS", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                                            activityResultLauncher.launch(i);
                                        }
                                    }).setCallback(new Snackbar.Callback(){
                                @Override
                                public void onDismissed(Snackbar snackbar, int event) {
                                    super.onDismissed(snackbar, event);
                                    if (event != DISMISS_EVENT_ACTION) {
                                        //will be true if user not click on Action button (for example: manual dismiss, dismiss by swipe
                                        finish();
                                    }
                                }
                            }).show();
                }
            }
            return;

        }
    }

    private void addActivityResultLauncher() {
        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_CANCELED){
                            checkPermissions();
                        }
                    }
                }
        );
    }


        private void checkPermissions(){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, CODE_UBI);
            }else{
                iniciarApp.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(user!=null){
                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        }else{
                            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                        }
                        finish();
                    }
                },2000);
            }
        }


}