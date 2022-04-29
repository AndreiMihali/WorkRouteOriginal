package com.example.workroute.profile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.profile.Profile;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

public class Personal_information extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RelativeLayout relativeChangeColors;
    private ImageView img_lockChange,imageProfile,img_editProfilePhoto;
    private TextView tv_unlock_save,tv_user_mail,tv_actualSubscription;
    private boolean sw = false;
    private TextInputEditText ed_name,ed_password;
    private TextInputLayout layout_ed_name,layout_ed_pass;
    private LinearLayout ln_subscription;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private final int CODE_GALLERY=100;
    private Uri uri;

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
        ed_name.setText(Companion.user.getNombre());

        tv_actualSubscription.setText(String.valueOf(Companion.user.getNivelSuscripcion()));

        /**
         * HAY QUE AVISAR QUE LA CONTRA DE GOOGLE NO PUEDE CAMBIARSE
         * SI LO QUE HAY EN sp.getString("Password"," ") DEVUELVE UNA CONTRASEÑA NO ES GOOGLE,
         * Tambien se puede pedir que introduzca la antigua contraseña.
         * Pero en caso de que quiera recuperarla porque se le olvido mejor no pedirla xd
         * EN CASO CONTRARIO ES DE GGOGLE
         */
        ed_password.setText(sp.getString("Password",""));

    }

    /**
     * DEJO HECHO EL METODO DE CAMBIO DE CONTRASEÑA PARA QUE LO TENGAS
     */

    private void changePassword(){
        progressDialog.show();
        String password=sp.getString("Password","");
        AuthCredential credential= EmailAuthProvider.getCredential(firebaseAuth.getCurrentUser().getEmail().toString(),password);
        firebaseAuth.getCurrentUser().reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.setTitle("Updating password");
                progressDialog.setMessage("Please wait...");
                //Aqui hay que meter la nueva contraseña que introduzca el usuario
                firebaseAuth.getCurrentUser().updatePassword(ed_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Snackbar.make(findViewById(R.id.rel_layout_general),"The password was changed successfully",Snackbar.LENGTH_SHORT).show();
                        sp.edit().putString("Password",ed_password.getText().toString()).commit();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("PASSWORD UPDATE","ERROR WHILE CHANGING THE PASSWORD"+e);
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("REAUTHENTICATE ERROR","ERROR WHILE REAUTHENTICATE"+e);
                progressDialog.dismiss();
            }
        });
    }

    private void main() {
        controls();
        initListeners();
        initActivityResultLauncher();
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
        tv_unlock_save = findViewById(R.id.textUnlock_Save);
        img_lockChange = findViewById(R.id.imageLock);
        ed_name = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_pass);
        ln_subscription = findViewById(R.id.layout_subscriptionLevel);
        tv_user_mail = findViewById(R.id.tv_userMail);
        firebaseAuth=FirebaseAuth.getInstance();
        imageProfile=findViewById(R.id.profileImage);
        img_editProfilePhoto = findViewById(R.id.editProfilePhoto);
        tv_actualSubscription=findViewById(R.id.tv_variableActualSubscription);
        sp=getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        progressDialog=new ProgressDialog(this,R.style.ProgressDialog);
        layout_ed_name=findViewById(R.id.layout_name);
        layout_ed_pass=findViewById(R.id.layout_pass);
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        relativeChangeColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sw==false) {
                    sw=true;
                    relativeChangeColors.setBackgroundColor(getColor(R.color.verde_flojo));
                    img_lockChange.setImageResource(R.drawable.ic_bx_lock_open);
                    tv_unlock_save.setText("SAVE CHANGES");
                    ed_name.setEnabled(true);
                    ed_password.setEnabled(true);
                } else if(sw) {
                    sw=false;
                    relativeChangeColors.setBackgroundColor(Color.parseColor("#DC1C1C"));
                    img_lockChange.setImageResource(R.drawable.ic_baseline_lock_24);
                    tv_unlock_save.setText("UNLOCK FIELDS");
                    ed_name.setEnabled(false);
                    ed_password.setEnabled(false);
                    //llamar a un metodo o algo de guardado de datos
                }

            }
        });

        ed_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(ed_password,layout_ed_pass);
                }else{
                    removeColorsFocus(ed_password,layout_ed_pass);
                }
            }
        });

        ed_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setColorsFocus(ed_name,layout_ed_name);
                }else{
                    removeColorsFocus(ed_name,layout_ed_name);
                }
            }
        });

        ln_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Personal_information.this, SubscribesActivity.class));
            }
        });

        img_editProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
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

    private void setColorsFocus(TextInputEditText ed, TextInputLayout txt){
        txt.setHintTextColor(ColorStateList.valueOf(getColor(R.color.secondary)));
        txt.setStartIconTintList(ColorStateList.valueOf(getColor(R.color.secondary)));
        ed.setTextColor(getColor(R.color.secondary));
        txt.setEndIconTintList(ColorStateList.valueOf(getColor(R.color.secondary)));
    }

    private void removeColorsFocus(TextInputEditText ed,TextInputLayout txt){
        ed.setTextColor(Color.parseColor("#BABABA"));
        txt.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
        txt.setStartIconTintList(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
        txt.setEndIconTintList(ColorStateList.valueOf(Color.parseColor("#D5D5D5")));
    }

}
