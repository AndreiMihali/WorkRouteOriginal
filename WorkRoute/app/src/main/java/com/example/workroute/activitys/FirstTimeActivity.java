package com.example.workroute.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.workroute.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.lang.ref.Reference;
import java.util.HashMap;

public class FirstTimeActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE_GALLERY = 100;
    private FloatingActionButton button_profile_photo;
    private ImageView profilePhoto;
    private MaterialCardView card_spinner,card_birthday;
    private Spinner spinnerCity;
    private TextView birthday;
    private MaterialButton button_continue;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri uri;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        init();
    }

    private void init(){
        button_profile_photo=findViewById(R.id.fab_camera);
        profilePhoto=findViewById(R.id.profileImage);
        card_spinner=findViewById(R.id.card_spiiner);
        card_birthday=findViewById(R.id.card_birthday);
        spinnerCity=findViewById(R.id.spinner_ciudad);
        birthday=findViewById(R.id.ed_birth);
        button_continue=findViewById(R.id.button_continue);
        firestore=FirebaseFirestore.getInstance();
        initActivityResultLauncher();
        initListeners();
    }

    private void initActivityResultLauncher() {
        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_OK){
                            Intent foto=result.getData();
                            uri=(Uri)foto.getData();
                            actualizarImagen();
                            Bitmap bitmap= null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            profilePhoto.setImageBitmap(bitmap);
                        }
                    }
                }
        );
    }

    private void initListeners() {
        button_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
    }


    private void abrirGaleria() {
        int  permissionCheck= ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_CODE_GALLERY);
        }else{
            llamarGaleria();
        }
    }

    private void llamarGaleria() {
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

    private void actualizarImagen(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(uri!=null){
                    StorageReference reference= FirebaseStorage.getInstance().getReference().child("imagenesPerfil/"+System.currentTimeMillis()+"_"+getFileExtension(uri));
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());
                                Uri downloadUri=uriTask.getResult();
                                String downloadUrl=downloadUri.toString();
                                HashMap<String,Object> hasMap=new HashMap<>();
                                hasMap.put("fotoPerfil",downloadUrl);

                                firestore.collection("Usuarios").document(FirebaseAuth.getInstance().getUid()).update(hasMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });
                        }
                    });
                }
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeType=MimeTypeMap.getSingleton();
        return mimeType.getExtensionFromMimeType(contentResolver.getType(uri));
    }



}