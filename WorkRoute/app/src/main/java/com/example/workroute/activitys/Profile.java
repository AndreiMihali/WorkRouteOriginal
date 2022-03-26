package com.example.workroute.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.adaptadores.AdapterTabsProfile;
import com.example.workroute.companion.Companion;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class Profile extends AppCompatActivity {
    private static final int PERMISSION_CODE_GALLERY = 100;
    private TextView userName;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private FloatingActionButton fab_updateImage;
    private ImageView profile;
    private MaterialButton editProfile;
    private ProgressBar progressLevel;
    private ViewPager2 viewPager2;
    private TextView followers;
    private TextView following;
    private TextView opinions;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri uri;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        main();
    }

    private void main() {
        controls();
        initListeners();
        setData();
        initActivityResult();
    }

    private void initActivityResult() {
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
                            profile.setImageBitmap(bitmap);
                        }
                    }
                }
        );
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
                                    Companion.user.setFotoPerfil(downloadUrl);
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

    private void setData() {
        userName.setText(Companion.user.getNombre());
        progressLevel.setProgress(Companion.user.getNivel());
        progressLevel.setMax(100);
        following.setText(Integer.toString(Companion.user.getFollowing()));
        followers.setText(Integer.toString(Companion.user.getFollowers()));
        opinions.setText(Integer.toString(Companion.user.getOpinions()));

        if(Companion.user.getFotoPerfil().equals("")){
            profile.setImageDrawable(getDrawable(R.drawable.default_user_login));
        }else{
            Glide.with(this).load(Companion.user.getFotoPerfil()).into(profile);
        }

    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar al main
                finish();

            }
        });

        fab_updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
    }

    private void controls() {
        toolbar=findViewById(R.id.toolbar);
        userName = findViewById(R.id.name);
        tabLayout=findViewById(R.id.tabLayout);
        profile=findViewById(R.id.imagen_perfil);
        fab_updateImage=findViewById(R.id.fab_camera);
        editProfile=findViewById(R.id.buttonEditProfile);
        progressLevel=findViewById(R.id.progress_lvl);
        viewPager2=findViewById(R.id.viewPager);
        followers=findViewById(R.id.followers);
        following=findViewById(R.id.following);
        opinions=findViewById(R.id.opinions);
        setSupportActionBar(toolbar);
        firestore=FirebaseFirestore.getInstance();

        /** AÑADIMOS ADAPTADOR AL TABLAYOUT*/

        AdapterTabsProfile adapter=new AdapterTabsProfile(this);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager2,
                (tab,postion)->tab.setText(adapter.getTabTitle(postion))).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }
}