package com.example.workroute.profile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import android.Manifest;
import android.app.Activity;
import android.app.Person;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.companion.UserType;
import com.example.workroute.profile.Profile;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Personal_information extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RelativeLayout relativeChangeColors;
    private ImageView img_lockChange,imageProfile,img_editProfilePhoto;
    private TextView tv_user_mail,txt_name,txt_work,txt_home;
    private MaterialCardView card_car;
    private TextView plateNumber,carType,carColor;
    private LinearLayout lnCarContent;
    private ImageButton btn_edit_work,btn_edit_home,btnUpDown;
    private boolean sw = false;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private final int CODE_GALLERY=100;
    private int AUTOCOMPLETE_REQUEST_CODE=1;
    private Uri uri;
    private int variableBandera=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rel_layout_general),(v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            Toolbar tool=findViewById(R.id.toolbarInformation);
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tool.getLayoutParams();
            mlp.leftMargin = insets.left;
            mlp.bottomMargin = insets.bottom;
            mlp.rightMargin = insets.right;
            mlp.topMargin=insets.top;
            tool.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });
        main();
        setData();
    }

    private void setData() {
        tv_user_mail.setText(firebaseAuth.getCurrentUser().getEmail().toString());
        if(Companion.user.getFotoPerfil().equals("")){
            imageProfile.setImageResource(R.drawable.default_user_login);
        }else{
            Glide.with(this).load(Companion.user.getFotoPerfil()).into(imageProfile);
        }
        txt_name.setText(Companion.user.getNombre());
        txt_work.setText(Companion.user.getWorkAddress());
        txt_home.setText(Companion.user.getLocalidad());
        if(!UserType.type.equals("driver")){
            card_car.setVisibility(View.GONE);
        }else{
            card_car.setVisibility(View.VISIBLE);
        }
    }

    private void main() {
        controls();
        initListeners();
        initActivityResultLauncher();
        getCarInformation();
    }

    private void initActivityResultLauncher() {
        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_OK){
                            Intent photo=result.getData();
                            uri=(Uri)photo.getData();
                            updateImage();
                            Bitmap bitmap= null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            imageProfile.setImageBitmap(bitmap);
                        }
                    }
                }
        );
    }

    private void getCarInformation(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Usuarios").child(firebaseAuth.getCurrentUser().getUid()).child("DriverInformation");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            plateNumber.setText(snapshot.child("PlateNumber").getValue().toString());
                            carColor.setText(snapshot.child("CarColor").getValue().toString());
                            carType.setText(snapshot.child("CarType").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


    private void updateImage() {
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

                            FirebaseFirestore.getInstance().collection("Usuarios").document(FirebaseAuth.getInstance().getUid()).update(hasMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });

                            FirebaseDatabase.getInstance().getReference().child("Usuarios").child(FirebaseAuth.getInstance().getUid()).updateChildren(hasMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void controls() {
        toolbar = findViewById(R.id.toolbarInformation);
        relativeChangeColors = findViewById(R.id.relativeChangingColorOnClick);
        img_lockChange = findViewById(R.id.imageLock);
        tv_user_mail = findViewById(R.id.tv_userMail);
        firebaseAuth=FirebaseAuth.getInstance();
        imageProfile=findViewById(R.id.profileImage);
        img_editProfilePhoto = findViewById(R.id.editProfilePhoto);
        txt_name=findViewById(R.id.txt_name);
        txt_home=findViewById(R.id.txt_home_address);
        txt_work=findViewById(R.id.txt_work_address);
        card_car=findViewById(R.id.card_car_information);
        btn_edit_home=findViewById(R.id.btn_edit_home);
        btn_edit_work=findViewById(R.id.btn_edit_work);
        progressDialog=new ProgressDialog(this,R.style.ProgressDialog);
        progressDialog.setTitle("Updating data");
        progressDialog.setMessage("We are updating your data, please wait...");
        btnUpDown=findViewById(R.id.buttonUpDown);
        carColor=findViewById(R.id.txt_car_color);
        carType=findViewById(R.id.txt_car_type);
        plateNumber=findViewById(R.id.txt_plateNumber);
        lnCarContent=findViewById(R.id.ln_information);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btnUpDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lnCarContent.getVisibility()==View.GONE){
                    lnCarContent.setVisibility(View.VISIBLE);
                    lnCarContent.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    btnUpDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }else if(lnCarContent.getVisibility()==View.VISIBLE){
                    lnCarContent.setVisibility(View.GONE);
                    btnUpDown.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
            }
        });

        relativeChangeColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw) {
                    updateFields();
                    relativeChangeColors.setBackgroundColor(Color.parseColor("#DC1C1C"));
                    img_lockChange.setImageResource(R.drawable.ic_baseline_lock_24);
                }
            }
        });


        img_editProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btn_edit_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variableBandera=1;
                startAutocompleteIntent();
            }
        });

        btn_edit_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variableBandera=2;
                startAutocompleteIntent();
            }
        });


    }

    private void startAutocompleteIntent(){
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setTypeFilter(TypeFilter.ADDRESS)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if(variableBandera==1){
                    txt_work.setText(place.getName());
                }else if(variableBandera==2){
                    txt_home.setText(place.getName());
                }
                relativeChangeColors.setBackgroundColor(getColor(R.color.verde_flojo));
                img_lockChange.setImageResource(R.drawable.ic_bx_lock_open);
                sw=true;

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                relativeChangeColors.setBackgroundColor(Color.parseColor("#DC1C1C"));
                img_lockChange.setImageResource(R.drawable.ic_baseline_lock_24);
                sw=false;
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateFields(){
        progressDialog.show();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("workAddress",txt_work.getText().toString());
        hashMap.put("localidad",txt_home.getText().toString());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FirebaseFirestore.getInstance().collection("Usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseDatabase.getInstance().getReference().child("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Companion.user.setWorkAddress(txt_work.getText().toString());
                                Companion.user.setLocalidad(txt_home.getText().toString());
                                progressDialog.dismiss();
                                Snackbar.make(findViewById(R.id.rel_layout_general),"The changes was saved succesfully",Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                });

            }
        });
    }

    private void openGallery() {
        int  permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_GALLERY);
        }else{
            callGallery();
        }
    }

    private void callGallery() {
        Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        activityResultLauncher.launch(gallery);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CODE_GALLERY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callGallery();
                }
            }
            break;
        }
        return;
    }

}
