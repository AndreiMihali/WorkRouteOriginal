package com.example.workroute.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.workroute.R;
import com.google.android.material.appbar.MaterialToolbar;

public class DrivingLicense extends AppCompatActivity {

    MaterialToolbar toolbarDrivingLicense;
    private ImageView imgFront;
    private ImageView imgReverse;
    private Button selectFront;
    private Button selectReverse;
    private Button saveLicense;

    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    public int lugarFoto=1;
    ActivityResultLauncher <Intent> activityResultLauncher;
    private static final int PERMISSION_CODE_GALLERY = 100;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license);
        main();
    }

    private void main() {
        controls();
        listeners();
        initActivityResultLauncher();
    }

    private void initActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode()==RESULT_OK) {
                    Intent foto=result.getData();
                    imageUri = (Uri) foto.getData();
                    if (lugarFoto==1) {
                        imgFront.setImageURI(imageUri);
                    } else if (lugarFoto==2) {
                        imgReverse.setImageURI(imageUri);
                    }
                } else if (result.getResultCode()==RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(),"Cancelled", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void controls() {
        toolbarDrivingLicense = findViewById(R.id.toolbar);
        imgFront = findViewById(R.id.imageCarnetFrente);
        imgReverse = findViewById(R.id.imageCarnetReverso);
        selectFront = findViewById(R.id.buttonFront);
        selectReverse = findViewById(R.id.buttonReverse);
        saveLicense = findViewById(R.id.buttonSaveLicense);
    }


    private void listeners() {
        toolbarDrivingLicense.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        selectFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lugarFoto = 1;
                abrirGaleria();
            }
        });

        selectReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lugarFoto = 2;
                abrirGaleria();
            }
        });

    }

    private void abrirGaleria( ) {
        int  permissionCheck= ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_CODE_GALLERY);
        }else{
            llamarGaleria();
        }
    }

    private void llamarGaleria( ) {
        Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        activityResultLauncher.launch(gallery);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE_GALLERY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    llamarGaleria();
                }
            }
            break;
        }
        return;
    }


    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(DrivingLicense.this).start();
        super.onDestroy();
    }
}
