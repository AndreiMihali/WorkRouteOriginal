package com.example.workroute.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.kotlin.activities.ChatsActivity;
import com.example.workroute.kotlin.activities.MessagesActivity;
import com.example.workroute.network.callback.NetworkCallback;
import com.example.workroute.profile.Profile;
import com.example.workroute.service.NotificationService;
import com.example.workroute.service.ServicioOnline;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.protobuf.Method;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomerMap extends FragmentActivity implements RoutingListener, LocationListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private FloatingActionButton button_menu, button_chats, button_profile, button_notifications, button_ubi;
    private Animation open, close, rotateForward, rotateBackWard;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private boolean isOpen = false;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    private GoogleApiClient googleApiClient;
    private Location myLastLocation;
    private LocationRequest locationRequest;
    private BottomSheetBehavior bottomSheetBehavior;
    private TextView txt_distance;
    private ImageView customer_photo;
    private TextView txt_name;
    private TextView txt_travelInformation;
    private ImageButton btn_message;
    private TextView txt_total_pay_travel;
    private TextView txt_startLocation;
    private TextView txt_destination;
    private MaterialButton btn_cancel;
    private List<Polyline> polylines;
    private List<Polyline> polylines2;
    private String driverId;
    private String driverDestination;
    private LatLng destinationDriverLatLng;
    private LatLng driverLatLng;
    private BiometricPrompt.PromptInfo biometricPrompt;
    private BiometricPrompt biometricPromptExecuter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_WorkRoute);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_map);
        new NetworkCallback().enable(this);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(uiOptions);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        getToken();
        init();
        startService(new Intent(this, ServicioOnline.class));
    }

    /**
     * OBTENEMOS EL TOKEN DE USUARIO PARA PODER GESTIONAR LAS NOTIFICACIONES
     */


    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        // Get new FCM registration token
                        String token = task.getResult();
                        saveToken(token);
                    }
                });
    }

    /**
     * GUARDAMOS EL TOKEN PARA PODER RECUPERARLO Y MANDAR LAS NOTIFICIONES A LOS USUARIOS
     * @param token
     */

    private void saveToken(String token) {
        reference = FirebaseDatabase.getInstance().getReference().child("Token");
        reference.child(FirebaseAuth.getInstance().getUid()).setValue(token);
    }

    private void init() {
        addActivityResultLauncher();
        button_menu = findViewById(R.id.buttonMenu);
        button_chats = findViewById(R.id.buttonMessages);
        button_profile = findViewById(R.id.buttonProfile);
        button_notifications = findViewById(R.id.button_notifications);
        //button_settings = findViewById(R.id.buttonSettings);
        button_ubi = findViewById(R.id.buttonUbi);
        open = AnimationUtils.loadAnimation(this, R.anim.open_menu);
        close = AnimationUtils.loadAnimation(this, R.anim.close_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this, R.style.ProgressDialog);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackWard = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        progressDialog.setCanceledOnTouchOutside(false);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.sheet));
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btn_cancel=findViewById(R.id.button_cancel);
        btn_message=findViewById(R.id.button_message_cost);
        polylines=new ArrayList<>();
        polylines2=new ArrayList<>();
        driverId="";
        setBiometricBuilder();
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        iniciarMapa();
        initListeners();
    }

    private void setBiometricBuilder() {
        if(BiometricManager.from(this).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG
                | BiometricManager.Authenticators.DEVICE_CREDENTIAL)==BiometricManager.BIOMETRIC_SUCCESS){

            biometricPromptExecuter=new BiometricPrompt(CustomerMap.this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback(){
                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    progressDialog.setTitle("Sending request");
                    progressDialog.setMessage("We are sending the request subscription to the driver...");
                    setPendingSubscription();
                }
            });

            biometricPrompt=new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric authentication")
                    .setSubtitle("Authenticate to complete the payment")
                    .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                    .build();

        }
    }

    private void setPendingSubscription() {
        progressDialog.show();
        DatabaseReference referenceDriverRequest=FirebaseDatabase.getInstance().getReference("Drivers").child(driverId).child("Requests").child(firebaseAuth.getUid());
        DatabaseReference referenceCustomerRequest=FirebaseDatabase.getInstance().getReference("Customers").child(firebaseAuth.getUid()).child("Requests").child(firebaseAuth.getUid());
        HashMap<String,Object> map=new HashMap<>();
        map.put("userId",firebaseAuth.getUid());
        map.put("status","pending");
        referenceDriverRequest.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("userId",firebaseAuth.getUid());
                    map.put("status","pending");
                    referenceCustomerRequest.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                View view=getLayoutInflater().inflate(R.layout.layout_confirmations,null);
                                Toast toast=new Toast(getApplicationContext());
                                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(view);
                                TextView message=view.findViewById(R.id.txt_description);
                                message.setText("We sent the request to the driver "+txt_name.getText().toString());
                                ImageView img=view.findViewById(R.id.image_confirmation);
                                img.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    toast.addCallback(new Toast.Callback() {
                                        @Override
                                        public void onToastHidden() {
                                            super.onToastHidden();
                                            btn_cancel.setText("Pending");
                                            getSenderName(firebaseAuth.getUid(),driverId);
                                        }
                                    });
                                }
                                toast.show();
                            }else{
                                progressDialog.dismiss();
                            }
                        }
                    });
                }else{
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Log.e("ERROR EN EL ENVIO DE PETICION",e.toString());
            }
        });
    }


    private void getSenderName(String sender,String receiverId){
        FirebaseFirestore.getInstance().collection("Usuarios").document(sender).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name=documentSnapshot.get("nombre").toString();
                getToken(name,receiverId,"Yo have a new request from "+name,"NEW REQUEST");
            }
        });
    }

    private void getToken(String sender,String receiver,String message, String title){
        FirebaseDatabase.getInstance().getReference().child("Token").child(receiver).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tokenUser=snapshot.getValue().toString();
                sendNotification(tokenUser,message,title,sender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(String token,String message, String title,String receiver) {
        new NotificationService(getApplicationContext(),token,message,title,receiver,"ActiveSubscriptions").start();
    }

    private boolean getDriversStarted=false;
    private List<Marker> markerList=new ArrayList<Marker>();
    private void getAllDrivers(){
        getDriversStarted=true;
        DatabaseReference driversReference=FirebaseDatabase.getInstance().getReference().child("locationUpdates").child("Drivers");
        GeoFire geoFire=new GeoFire(driversReference);
        GeoQuery geoQuery=geoFire.queryAtLocation(new GeoLocation(myLastLocation.getLatitude(),myLastLocation.getLongitude()),300000);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                for(Marker markerIt:markerList){
                    if(markerIt.getTag().toString().equals(key)){
                        return;
                    }
                }
                LatLng driverLocation=new LatLng(location.latitude,location.longitude);

                Marker mDriverMarker=mMap.addMarker(new MarkerOptions().position(driverLocation));
                mDriverMarker.setTag(key);
                markerList.add(mDriverMarker);
            }

            @Override
            public void onKeyExited(String key) {
                for(Marker markerIt:markerList){
                    if (markerIt.getTag().equals(key)) {
                        markerIt.remove();
                        markerList.remove(markerIt);
                        return;
                    }
                }
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                for(Marker markerIt:markerList){
                    if(markerIt.getTag().equals(key)){
                        markerIt.setPosition(new LatLng(location.latitude,location.longitude));
                    }
                }
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void getDriverDestination(String driverId){
        DatabaseReference driverDestinationRef=FirebaseDatabase.getInstance().getReference().child("Drivers").child(driverId).child("destinations");
        driverDestinationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String,Object> map=(Map<String, Object>) snapshot.getValue();
                    if(map.get("destination")!=null){
                        driverDestination=map.get("destination").toString();
                    }else{
                        driverDestination="--";
                    }
                    Double destinationLat=0.0;
                    Double destinationLong=0.0;

                    if(map.get("destinationLat")!=null){
                        destinationLat=Double.valueOf(map.get("destinationLat").toString());
                    }

                    if(map.get("destinationLong")!=null){
                        destinationLong=Double.valueOf(map.get("destinationLong").toString());
                        destinationDriverLatLng=new LatLng(destinationLat,destinationLong);
                    }

                    getRouteToMarker(driverLatLng,new LatLng(myLastLocation.getLatitude(),myLastLocation.getLongitude()));
                    getDriverInfo(driverId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDriverInfo(String driverId) {
        displayInformationDriver(BottomSheetBehavior.STATE_EXPANDED);
        DatabaseReference customerInfoRef=FirebaseDatabase.getInstance().getReference().child("Usuarios").child(driverId);
        customerInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0){
                    Map<String,Object> map=(Map<String, Object>) snapshot.getValue();
                    if(map.get("nombre")!=null){
                        txt_name.setText(map.get("nombre").toString());
                    }
                    if(map.get("fotoPerfil")!=null){
                        if(map.get("fotoPerfil").toString().equals("")){
                            customer_photo.setImageResource(R.drawable.default_user_login);
                        }else{
                            Glide.with(getApplicationContext()).load(map.get("fotoPerfil").toString()).into(customer_photo);
                        }
                    }
                    txt_startLocation.setText(getGeocoderAddress(new LatLng(myLastLocation.getLatitude(),myLastLocation.getLongitude())));
                    txt_destination.setText(driverDestination);
                    btn_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(CustomerMap.this, MessagesActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra("userId", driverId)
                                    .putExtra("userName", map.get("nombre").toString())
                                    .putExtra("userPhoto", map.get("fotoPerfil").toString());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getRouteToMarker(LatLng startDestination,LatLng myPosition){
        Routing routing=new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(startDestination,myPosition)
                .key(getString(R.string.google_maps_key))
                .build();
        routing.execute();
        getRouteToDestination(startDestination);
    }

    private Marker markerFinal;
    private void getRouteToDestination(LatLng startDestination){
        Routing routing=new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(new RoutingListener() {
                    @Override
                    public void onRoutingFailure(RouteException e) {

                    }

                    @Override
                    public void onRoutingStart() {

                    }

                    @Override
                    public void onRoutingSuccess(ArrayList<Route> route, int lastPositionIndex) {
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                if(polylines2.size()>0) {
                                    for (Polyline poly : polylines2) {
                                        poly.remove();
                                    }
                                }

                                polylines2 = new ArrayList<>();
                                //add route(s) to the map.
                                for (int i = 0; i <route.size(); i++) {
                                    //In case of more than 5 alternative routes
                                    PolylineOptions polyOptions = new PolylineOptions();
                                    polyOptions.color(getColor(R.color.secondaryLine));
                                    polyOptions.width(15 + i * 10);
                                    polyOptions.addAll(route.get(i).getPoints());
                                    Polyline polyline = mMap.addPolyline(polyOptions);
                                    polylines2.add(polyline);
                                }
                                markerFinal= mMap.addMarker(new MarkerOptions().position(destinationDriverLatLng).icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(getDrawable(R.drawable.ic_baseline_location_on_24))))
                                .flat(true).anchor(0.5f,0.5f));
                            }
                        });
                    }

                    @Override
                    public void onRoutingCancelled() {

                    }
                })
                .alternativeRoutes(false)
                .waypoints(startDestination,destinationDriverLatLng)
                .key(getString(R.string.google_maps_key))
                .build();
        routing.execute();
    }

    private void initListeners() {
        /** MÉTODO PARA AÑADIR ICONOS DE NOTIFICACIÓN A LOS BOTONES*/
        button_notifications.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("UnsafeOptInUsageError")
            @Override
            public void onGlobalLayout() {
                BadgeDrawable badgeDrawable = BadgeDrawable.create(CustomerMap.this);
                badgeDrawable.setNumber(2);
                //Important to change the position of the Badge
                badgeDrawable.setHorizontalOffset(30);
                badgeDrawable.setVerticalOffset(20);

                BadgeUtils.attachBadgeDrawable(badgeDrawable, button_notifications, null);

                button_notifications.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateMenu();
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState==BottomSheetBehavior.STATE_HIDDEN){
                    erasePolylines();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        button_ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myLastLocation!=null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLastLocation.getLatitude(),myLastLocation.getLongitude()),12f));
                }
            }
        });

        button_chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerMap.this, ChatsActivity.class);
                startActivity(i);
                animateMenu();
            }
        });

        button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerMap.this, Profile.class);
                startActivity(i);
                animateMenu();
            }
        });


        /**
         * BOTÓN SUBSCRIBE
         */
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    private void showDialog(){
        new MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setCancelable(false)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to subscribe to this driver?")
                .setPositiveButton("SUBSCRIBE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        havePayMethod();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }


    private void havePayMethod(){
            ValueEventListener postListener=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getChildrenCount()>0){
                        if (biometricPrompt==null) {
                            //TODO : TOAST PERSONALIZADO PARA METER EL CVV PARA ACEPTAR EL PAGO
                            View view=getLayoutInflater().inflate(R.layout.message_toast_type_cvv,null);
                            AlertDialog.Builder MyAlert = new AlertDialog.Builder(getApplicationContext(),R.style.DialogAlert);
                            MyAlert.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            MyAlert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            MyAlert.setView(view);
                            AlertDialog dialog = MyAlert.create();
                            dialog.show();
                        }else {
                            biometricPromptExecuter.authenticate(biometricPrompt);
                        }
                    }else{
                        startActivity(new Intent(CustomerMap.this,PayMethod.class));
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            };

            FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().getUid()).child("payMethods").addListenerForSingleValueEvent(postListener);
    }

    /**
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     *
     *                             AQUI EMPIEZAN LOS METODOS QUE TIENEN QUE VER CON EL MAPA DE LOS COJONES
     *
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     ***********************************************************************************************************************************/

    /**
     * METODO PARA PEDIR UN VIAJE A UN CONDUCTOR
     */


    //Método para resetear los campos de informacion del conducotr en el sheet
    private void clearAllCustomerInformation(){
        txt_distance.setText("");
        customer_photo.setImageResource(R.drawable.default_user_login);
        txt_name.setText("");
        txt_travelInformation.setText("");
        txt_total_pay_travel.setText("");
        txt_startLocation.setText("");
        txt_destination.setText("--");
    }

    /**
     * INICIO METODOS PARA DESPLEGAR LOS BOTTOM SHEETS
     * @param state
     */


    private void displayInformationDriver(int state){
        txt_distance=findViewById(R.id.txt_distance_cost);
        customer_photo=findViewById(R.id.profile_photo_sheet_cost);
        txt_name=findViewById(R.id.txt_name_cost);
        txt_travelInformation=findViewById(R.id.txt_travel_information_cost);
        txt_total_pay_travel=findViewById(R.id.txt_total_pay_travel_cost);
        txt_startLocation=findViewById(R.id.txt_startLocation_cost);
        txt_destination=findViewById(R.id.txt_destination_cost);

        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(state);
        bottomSheetBehavior.setSaveFlags(BottomSheetBehavior.SAVE_ALL);

    }

    /**
     * FIN METODOS PARA DESPLEGAR LOS BOTTOM SHEETS
     * @param state
     */


    /**
     * UNA VEZ ENCONTRADO EL CONDUCTOR RECOGEMOS SUS DATOS PARA MOSTRARLO
     */


    private String getGeocoderAddress(LatLng location){
        Geocoder geocoder=new Geocoder(CustomerMap.this, Locale.getDefault());
        String address="";
        try{
            List<Address> listAddress=geocoder.getFromLocation(location.latitude,location.longitude,1);
            if(listAddress.size()>0){
                address=listAddress.get(0).getAddressLine(0);
            }
        }catch (IOException e){
            Log.e("Error",e.toString());
        }
        return address;
    }
    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(CustomerMap.this).start();
        super.onDestroy();
    }

    private void animateMenu() {
        if (isOpen) {
            button_menu.startAnimation(rotateForward);
            button_menu.setImageResource(R.drawable.ic_baseline_menu_24);


            button_chats.startAnimation(close);
            button_profile.startAnimation(close);
            button_notifications.startAnimation(close);
            //button_settings.startAnimation(close);


            button_chats.setVisibility(View.INVISIBLE);
            button_profile.setVisibility(View.INVISIBLE);
            button_notifications.setVisibility(View.INVISIBLE);
            //button_settings.setVisibility(View.INVISIBLE);


            button_chats.setClickable(false);
            button_profile.setClickable(false);
            button_notifications.setClickable(false);
            //button_settings.setClickable(false);


            isOpen = false;
        } else {
            button_menu.startAnimation(rotateBackWard);
            button_menu.setImageResource(R.drawable.ic_baseline_menu_open_24);
            button_chats.startAnimation(open);
            button_profile.startAnimation(open);
            button_notifications.startAnimation(open);
            //button_settings.startAnimation(open);



            button_chats.setVisibility(View.VISIBLE);
            button_profile.setVisibility(View.VISIBLE);
            button_notifications.setVisibility(View.VISIBLE);
            //button_settings.setVisibility(View.VISIBLE);


            button_chats.setClickable(true);
            button_profile.setClickable(true);
            button_notifications.setClickable(true);
            //button_settings.setClickable(true);

            isOpen = true;
        }
    }


    private void iniciarMapa() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapCustomer);
        mapFragment.getMapAsync(this::onMapReady);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
        buildGoogleApiClient();
    }

    private LocationManager locationManager;

    private boolean isLocationEnabled(){
        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            return true;
        }else{
            return false;
        }
    }

    private void showDialogMessageGpsEnable(){

        new MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle("GPS DISABLED")
                .setIcon(R.drawable.ic_baseline_gps_off_24)
                .setMessage("In order to continue, please activate the gps")
                .setCancelable(false)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(CustomerMap.this,MainActivity.class));
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("ACTIVATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enableGps();
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void enableGps() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activityResultLauncher.launch(settingsIntent);
    }

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private void addActivityResultLauncher() {
        activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()==RESULT_CANCELED){
                           buildGoogleApiClient();
                        }
                    }
                }
        );
    }


    protected synchronized void buildGoogleApiClient() {
        if(isLocationEnabled()){
            googleApiClient=new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();
        }else{
            showDialogMessageGpsEnable();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(getApplicationContext()!=null){
            myLastLocation=location;
            LatLng latlng=new LatLng(location.getLatitude(),location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,12f));
            setMyLocationInDatabase(location);
            setDestination();
            if(!getDriversStarted){
                getAllDrivers();
            }

            LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
            button_ubi.setVisibility(View.VISIBLE);
        }
    }

    private void setDestination(){
        DatabaseReference customerReference=FirebaseDatabase.getInstance().getReference("Customers");
        Map<String,Object> map=new HashMap<>();
        map.put("destination", Companion.user.getWorkAddress());
        map.put("destinationLat",getDestination(Companion.user.getWorkAddress(),"latitude"));
        map.put("destinationLong",getDestination(Companion.user.getWorkAddress(),"longitude"));
        customerReference.child(firebaseAuth.getUid()).child("destinations").updateChildren(map);
    }

    private void setMyLocationInDatabase(Location location){
        String userId=firebaseAuth.getUid();
        DatabaseReference customerReference=FirebaseDatabase.getInstance().getReference("locationUpdates").child("Customers");
        GeoFire geoFire=new GeoFire(customerReference);
        geoFire.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));
    }


    private Double getDestination(String destination,String field){
        Geocoder geocoder = new Geocoder(CustomerMap.this, Locale.getDefault());
        Double address = 0.0;
        try {
            List<Address> listAddress = geocoder.getFromLocationName(destination,1);
            if (listAddress.size() > 0) {
                if(field.equals("latitude")){
                    address = listAddress.get(0).getLatitude();
                }else if(field.equals("longitude")){
                    address=listAddress.get(0).getLongitude();
                }

            }
        } catch (IOException e) {
            Log.e("Error", e.toString());
        }
        return address;
    }

    private LocationCallback locationCallback;
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     *
     *                             AQUI TERMINAN LOS METODOS QUE TIENEN QUE VER CON EL MAPA DE LOS COJONES
     *
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     ***********************************************************************************************************************************/


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        erasePolylines();
        if(!marker.getTag().toString().equals(firebaseAuth.getUid())&&(marker.getTag()!=null||marker.getTag().toString().equals(""))){
            driverId=marker.getTag().toString();
            driverLatLng= marker.getPosition();
            getDriverDestination(driverId);
        }
        return false;
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int fastestRoute) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(polylines.size()>0) {
                    for (Polyline poly : polylines) {
                        poly.remove();
                    }
                }

                polylines = new ArrayList<>();
                //add route(s) to the map.
                for (int i = 0; i <route.size(); i++) {
                    //In case of more than 5 alternative routes
                    PolylineOptions polyOptions = new PolylineOptions();
                    polyOptions.color(getColor(R.color.secondary));
                    polyOptions.width(15 + i * 10);
                    polyOptions.addAll(route.get(i).getPoints());
                    Polyline polyline = mMap.addPolyline(polyOptions);
                    polylines.add(polyline);
                    txt_travelInformation.setText("Your driver will arrive in " + route.get(i).getDurationText());
                    txt_distance.setText("Your driver is "+route.get(i).getDistanceText()+" away");
                }
            }
        });
    }

    @Override
    public void onRoutingCancelled() {

    }

    private void erasePolylines(){
        if(markerFinal!=null){
            markerFinal.remove();
        }
        for(Polyline line:polylines){
            line.remove();
        }
        polylines.clear();

        for(Polyline line:polylines2){
            line.remove();
        }
        polylines2.clear();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if(locationCallback!=null){
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }

    private Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

}

