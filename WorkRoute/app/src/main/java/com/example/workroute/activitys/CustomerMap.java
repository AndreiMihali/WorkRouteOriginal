package com.example.workroute.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.driverActivities.DriverMap;
import com.example.workroute.kotlin.activities.ChatsActivity;
import com.example.workroute.kotlin.activities.MessagesActivity;
import com.example.workroute.network.callback.NetworkCallback;
import com.example.workroute.profile.Profile;
import com.example.workroute.service.ServicioOnline;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CustomerMap extends FragmentActivity implements RoutingListener,com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private FloatingActionButton button_menu, button_chats, button_profile, button_notifications, button_ubi;
    private Animation open, close, rotateForward, rotateBackWard;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private boolean isOpen = false;
    private boolean isOpen2=false;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    private GoogleApiClient googleApiClient;
    private Location myLastLocation;
    private LocationRequest locationRequest;
    private BottomSheetBehavior bottomSheetBehavior;
    private boolean requestBol=false;
    private Marker pickupMarker;
    private String destination;
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
    private static final int[] COLORS = new int[]{R.color.secondary};
    private AutocompleteSupportFragment autocompleteFragment;
    private LatLng destinationLatLng;
    private String driverDestination;
    private LatLng destinationDriverLatLng;
    private Marker myPositionMarker;
    private LatLng driverLatLng;

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
        destinationLatLng=new LatLng(0.0,0.0);
        destination="";
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        iniciarMapa();
        initListeners();

    }

    private boolean getDriversStarted=false;
    private List<Marker> markerList=new ArrayList<Marker>();
    private void getAllDrivers(){
        getDriversStarted=true;
        DatabaseReference driversReference=FirebaseDatabase.getInstance().getReference().child("Drivers");
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
                    txt_startLocation.setText(getGeocoderAddress());
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

    private void getRouteToMarker(LatLng pickUpMarker,LatLng startDestination){
        Routing routing=new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(startDestination,pickUpMarker)
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

            }
        });
        
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


    private String getGeocoderAddress(){
        Geocoder geocoder=new Geocoder(CustomerMap.this, Locale.getDefault());
        String address="";
        try{
            List<Address> listAddress=geocoder.getFromLocation(myLastLocation.getLatitude(),myLastLocation.getLongitude(),1);
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
        DatabaseReference customerReference=FirebaseDatabase.getInstance().getReference("Customers");
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
        if(!marker.getTag().toString().equals(firebaseAuth.getUid())){
            String driverId=marker.getTag().toString();
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
        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i <route.size(); i++) {
            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(25 + i * 20);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            txt_travelInformation.setText("Your driver will arrive in " + route.get(i).getDurationText());
            txt_distance.setText("Your driver is "+route.get(i).getDistanceText()+" away");


        }

    }

    @Override
    public void onRoutingCancelled() {

    }

    private void erasePolylines(){
        for(Polyline line:polylines){
            line.remove();
        }
        polylines.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if(locationCallback!=null){
            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }
}

