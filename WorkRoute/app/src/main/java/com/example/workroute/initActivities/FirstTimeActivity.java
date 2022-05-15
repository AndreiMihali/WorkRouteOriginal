package com.example.workroute.initActivities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.workroute.R;
import com.example.workroute.activitys.MainActivity;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class FirstTimeActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE_GALLERY = 100;
    private FloatingActionButton button_profile_photo;
    private ImageView profilePhoto;
    private MaterialCardView card_spinner, card_birthday, card_work_address;
    private TextView locality, birth, workAddress;
    private MaterialButton button_continue;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri uri;
    private FirebaseFirestore firestore;
    private TextView username;
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private String fechaNac;
    private ProgressDialog progressDialog;
    private int variableBandera = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        new NetworkCallback().enable(this);
        init();
    }

    private void init() {
        button_profile_photo = findViewById(R.id.fab_camera);
        profilePhoto = findViewById(R.id.profileImage);
        card_spinner = findViewById(R.id.card_spiiner);
        card_birthday = findViewById(R.id.card_birthday);
        locality = findViewById(R.id.txt_ciudad);
        birth = findViewById(R.id.txt_cumple);
        button_continue = findViewById(R.id.button_continue);
        firestore = FirebaseFirestore.getInstance();
        username = findViewById(R.id.user_name);
        progressDialog = new ProgressDialog(this);
        card_work_address = findViewById(R.id.card_work_direction);
        workAddress = findViewById(R.id.txt_work_direction);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        Bundle bundle = getIntent().getExtras();
        if(bundle==null){
            username.setText(getSharedPreferences(getString(R.string.sharedPreferences),Context.MODE_PRIVATE).getString("name","default name"));
        }else{
            username.setText(bundle.getString("Name", "Username"));
        }
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        initActivityResultLauncher();
        initListeners();
    }

    private void initActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent foto = result.getData();
                            uri = (Uri) foto.getData();
                            actualizarImagen();
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
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

        card_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variableBandera = 1;
                startAutocompleteIntent();
            }
        });

        card_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        card_work_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variableBandera = 2;
                startAutocompleteIntent();
            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData(v);
            }
        });

    }

    private void checkData(View v) {
        if (birth.getText().toString().trim().equals("Choose your day of birth")
                || locality.getText().toString().trim().equals("Enter your home address")
                || workAddress.getText().toString().trim().equals("Enter your work address")
                || getGeocoderAddress(workAddress.getText().toString())==null) {
            if(getGeocoderAddress(workAddress.getText().toString())==null){
                Snackbar.make(v, "You must introduce a valid work address", Snackbar.LENGTH_SHORT).show();
            }else{
                Snackbar.make(v, "You need to fill all fields", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            actualizarDatos(locality.getText().toString().trim(), "localidad");
            actualizarDatos(workAddress.getText().toString().trim(), "workAddress");
            actualizarDatos(1, "vecesConectadas");
            actualizarDatos(calcularEdad(fechaNac), "edad");
            actualizarDatos(fechaNac, "fecha_naci");
            actualizarDatos(getGeocoderAddress(workAddress.getText().toString()),"postalCodeWork");
            getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit().putInt("faseConexion", 2).commit();
            startActivity(new Intent(FirstTimeActivity.this, MainActivity.class));
            this.finish();
        }
    }


    private void openDatePicker() {

        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())
                .build();

        MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your day of birth")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.CalendarPicker)
                .setCalendarConstraints(constraints)
                .build();

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                birth.setText(picker.getHeaderText());
                TimeZone timeZoneUTC = TimeZone.getDefault();
                int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                Date date = new Date((Long) selection + offsetFromUTC);
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                fechaNac = format.format(date);
                picker.dismiss();
            }
        });


        picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.dismiss();
            }
        });

        picker.show(getSupportFragmentManager(), "DATE PICKER");
    }


    private void abrirGaleria() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE_GALLERY);
        } else {
            llamarGaleria();
        }
    }

    private void llamarGaleria() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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

    private void actualizarImagen() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (uri != null) {
                    StorageReference reference = FirebaseStorage.getInstance().getReference().child("imagenesPerfil/" + System.currentTimeMillis() + "_" + getFileExtension(uri));
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadUri = uriTask.getResult();
                            String downloadUrl = downloadUri.toString();
                            HashMap<String, Object> hasMap = new HashMap<>();
                            hasMap.put("fotoPerfil", downloadUrl);

                            firestore.collection("Usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(hasMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });

                            FirebaseDatabase.getInstance().getReference().child("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hasMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeType = MimeTypeMap.getSingleton();
        return mimeType.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void startAutocompleteIntent() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setTypeFilter(TypeFilter.ADDRESS)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private String getGeocoderAddress(String name) {
        Geocoder geocoder = new Geocoder(FirstTimeActivity.this, Locale.getDefault());
        String address = "";
        try {
            List<Address> listAddress = geocoder.getFromLocationName(name,1);
            if (listAddress.size() > 0) {
                address = listAddress.get(0).getPostalCode();
            }
        } catch (IOException e) {
            Log.e("Error", e.toString());
        }
        return address;
    }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (variableBandera == 1) {
                    locality.setText(place.getName());
                } else if (variableBandera == 2) {
                    workAddress.setText(place.getName());
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private int calcularEdad(String fecha) {
        Date fechaNac = null;
        try {
            fechaNac = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fecha);
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        Calendar fechaActual = Calendar.getInstance();
        fechaNacimiento.setTime(fechaNac);

        int año = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
        int mes = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE) - fechaNacimiento.get(Calendar.DATE);

        if (mes < 0 || (mes == 0 && dia < 0)) {
            año--;
        }

        return año;
    }

    private void actualizarDatos(Object dato, String campo) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(campo, dato);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                firestore.collection("Usuarios").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Gucci", "Todo gucci");
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });

                FirebaseDatabase.getInstance().getReference().child("Usuarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });
    }
}
