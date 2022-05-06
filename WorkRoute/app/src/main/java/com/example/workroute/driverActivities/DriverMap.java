package com.example.workroute.driverActivities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.workroute.R;
import com.example.workroute.activitys.CustomerMap;
import com.example.workroute.activitys.MainActivity;
import com.example.workroute.companion.Companion;
import com.example.workroute.companion.UserType;
import com.example.workroute.kotlin.activities.ChatsActivity;
import com.example.workroute.kotlin.activities.MessagesActivity;
import com.example.workroute.network.callback.NetworkCallback;
import com.example.workroute.notifications.Notifications;
import com.example.workroute.profile.Profile;
import com.example.workroute.service.ServicioOnline;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DriverMap extends FragmentActivity implements com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    private FloatingActionButton button_menu, button_chats, button_profile, button_notifications, button_ubi;
    private Animation open, close, rotateForward, rotateBackWard;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private boolean isOpen = false;
    private boolean isOpen2 = false;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    private GoogleApiClient googleApiClient;
    private Location myLastLocation;
    private LocationRequest locationRequest;
    private BottomSheetBehavior bottomSheetBehavior;
    private String customerId = "";
    private TextView txt_distance;
    private ImageView customer_photo;
    private TextView txt_name;
    private TextView txt_travelInformation;
    private ImageButton btn_message;
    private TextView txt_total_pay_travel;
    private TextView txt_startLocation;
    private TextView txt_destination;
    private List<Polyline> polylines;
    private List<Polyline> polylines2;
    private LocationCallback locationCallback;
    private MaterialButton btnRideStatus;
    private Marker myPositionMarker;
    private String customerDestination;
    private LatLng destinationCustomerLatLng;
    private LatLng customerLatLng;
    private TextView txt_statusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_WorkRoute);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        new NetworkCallback().enable(this);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(uiOptions);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        UserType.type="driver";
        getToken();
        init();
        startService(new Intent(this, ServicioOnline.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(circleMap!=null&&myWork!=null&&myHome!=null){
            myWork.remove();
            myHome.remove();
            circleMap.remove();
            setDestination();
        }
    }

    private boolean getCustomersAroundStarted=false;
    private List<Marker> markerList=new ArrayList<Marker>();
    private void getAllCustomers(){
        getCustomersAroundStarted=true;
        DatabaseReference customersReference=FirebaseDatabase.getInstance().getReference().child("locationUpdates").child("Customers");
        GeoFire geoFire=new GeoFire(customersReference);
        GeoQuery geoQuery=geoFire.queryAtLocation(new GeoLocation(myLastLocation.getLatitude(),myLastLocation.getLongitude()),300000);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                for(Marker markerIt:markerList){
                    if(markerIt.getTag().toString().equals(key)){
                        markerIt.remove();
                        return;
                    }
                }
                LatLng customerLocation=new LatLng(location.latitude,location.longitude);

                if (!key.equals(firebaseAuth.getUid())) {
                    Marker mCustomerMarker=mMap.addMarker(new MarkerOptions().position(customerLocation).icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(getDrawable(R.drawable.favorite))))
                    .flat(true).anchor(0.5f,0.5f));
                    mCustomerMarker.setTag(key);
                    markerList.add(mCustomerMarker);
                }
            }

            @Override
            public void onKeyExited(String key) {
                for (Marker markerIt:markerList){
                    if(markerIt.getTag().equals(key)){
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

    private void getCustomerDestination(String customerId){
        DatabaseReference customerDestinationRef=FirebaseDatabase.getInstance().getReference().child("Customers").child(customerId).child("destinations");
        customerDestinationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String,Object> map=(Map<String, Object>) snapshot.getValue();
                    if(map.get("destination")!=null){
                        customerDestination=map.get("destination").toString();
                    }else{
                        customerDestination="--";
                    }
                    Double destinationLat=0.0;
                    Double destinationLong=0.0;

                    if(map.get("destinationLat")!=null){
                        destinationLat=Double.valueOf(map.get("destinationLat").toString());
                    }

                    if(map.get("destinationLong")!=null){
                        destinationLong=Double.valueOf(map.get("destinationLong").toString());
                        destinationCustomerLatLng=new LatLng(destinationLat,destinationLong);
                    }

                    getRouteToMarker(customerLatLng,new LatLng(myLastLocation.getLatitude(),myLastLocation.getLongitude()));
                    getCustomerInfo(customerId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getRouteToMarker(LatLng customerPosition,LatLng myPosition){
        Routing routing=new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(myPosition,customerPosition)
                .key(getString(R.string.google_maps_key))
                .build();
        routing.execute();
        getRouteToDestination(customerPosition);
    }

    private Marker markerFinal;
    private Circle circleMap;
    private Marker myWorkMarker;
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
                    public void onRoutingSuccess(ArrayList<Route> route, int lstPositionIndex) {
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
                                markerFinal=mMap.addMarker(new MarkerOptions().position(destinationCustomerLatLng).icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(getDrawable(R.drawable.flag))))
                                        .flat(true).anchor(0.5f,0.5f));
                            }
                        });
                    }

                    @Override
                    public void onRoutingCancelled() {

                    }
                })
                .alternativeRoutes(false)
                .waypoints(startDestination,destinationCustomerLatLng)
                .key(getString(R.string.google_maps_key))
                .build();
        routing.execute();
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

    private void getCustomerInfo(String customerId){
        displayInformationCustomer(BottomSheetBehavior.STATE_EXPANDED);
        DatabaseReference customerInfoRef=FirebaseDatabase.getInstance().getReference().child("Usuarios").child(customerId);
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
                    txt_destination.setText(customerDestination);
                    btn_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(DriverMap.this, MessagesActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra("userId", customerId)
                                    .putExtra("userName", map.get("nombre").toString())
                                    .putExtra("userPhoto", map.get("fotoPerfil").toString());
                            startActivity(intent);
                        }
                    });
                    if(isPendingSubscribed(customerId).equals("pending")){
                        txt_statusMessage.setVisibility(View.VISIBLE);
                        txt_statusMessage.setText("You have to accept the user request");
                        btnRideStatus.setVisibility(View.GONE);
                        txt_total_pay_travel.setText("20$/month");
                    }else if(isPendingSubscribed(customerId).equals("declined")||isPendingSubscribed(customerId).equals("canceled")){
                        txt_statusMessage.setVisibility(View.VISIBLE);
                        txt_statusMessage.setText("You canceled or declined the user request");
                        btnRideStatus.setVisibility(View.GONE);
                        txt_total_pay_travel.setText("0$/month");
                    }else if(isPendingSubscribed(customerId).equals("accepted")){
                        txt_statusMessage.setVisibility(View.GONE);
                        btnRideStatus.setVisibility(View.VISIBLE);
                        btnRideStatus.setText("PickUp customer");
                        txt_total_pay_travel.setText("20$/month");
                    }else{
                        txt_statusMessage.setVisibility(View.VISIBLE);
                        txt_statusMessage.setText("This user isn`t subscribed yet");
                        btnRideStatus.setVisibility(View.GONE);
                        txt_total_pay_travel.setText("0$/month");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String status="";
    private String isPendingSubscribed(String customerId){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Drivers").child(FirebaseAuth.getInstance().getUid()).child("Requests");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snap:snapshot.getChildren()){
                        if(snap.child("userId").getValue().toString().equals(customerId)){
                            status=  snap.child("status").getValue().toString();
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return status;
    }

    private String getGeocoderAddress(){
        Geocoder geocoder=new Geocoder(DriverMap.this,Locale.getDefault());
        String address="";
        try{
            List<Address> listAddress=geocoder.getFromLocation(customerLatLng.latitude,customerLatLng.longitude,1);
            if(listAddress.size()>0){
                address=listAddress.get(0).getAddressLine(0);
            }
        }catch (IOException e){
            Log.e("Error",e.toString());
        }
        return address;
    }

    private void displayInformationCustomer(int state) {
        txt_distance = findViewById(R.id.txt_distance);
        customer_photo = findViewById(R.id.profile_photo_sheet);
        txt_name = findViewById(R.id.txt_name);
        txt_travelInformation = findViewById(R.id.txt_travel_information);
        txt_total_pay_travel = findViewById(R.id.txt_total_pay_travel);
        txt_startLocation = findViewById(R.id.txt_startLocation);
        txt_destination = findViewById(R.id.txt_destination);
        txt_statusMessage= findViewById(R.id.txt_status_message);

        bottomSheetBehavior.setPeekHeight(200);
        bottomSheetBehavior.setState(state);
        bottomSheetBehavior.setSaveFlags(BottomSheetBehavior.SAVE_ALL);
    }

    private void clearAllCustomerInformation() {
        txt_distance.setText("");
        customer_photo.setImageResource(R.drawable.default_user_login);
        txt_name.setText("");
        txt_travelInformation.setText("");
        txt_total_pay_travel.setText("");
        txt_startLocation.setText("");
        txt_destination.setText("--");
    }


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
        progressDialog = new ProgressDialog(this, R.style.ProgressDialog);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackWard = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        progressDialog.setMessage("Checking your location. This can take a while...");
        progressDialog.setCanceledOnTouchOutside(false);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.sheet));
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btn_message = findViewById(R.id.button_message);
        polylines = new ArrayList<>();
        polylines2=new ArrayList<>();
        btnRideStatus = findViewById(R.id.btn_rideStatus);
        iniciarMapa();
        initListeners();
    }

    private void initListeners() {
        /** MÉTODO PARA AÑADIR ICONOS DE NOTIFICACIÓN A LOS BOTONES*/
        button_notifications.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("UnsafeOptInUsageError")
            @Override
            public void onGlobalLayout() {
                BadgeDrawable badgeDrawable = BadgeDrawable.create(DriverMap.this);
                badgeDrawable.setNumber(2);
                //Important to change the position of the Badge
                badgeDrawable.setHorizontalOffset(30);
                badgeDrawable.setVerticalOffset(20);

                BadgeUtils.attachBadgeDrawable(badgeDrawable, button_notifications, null);

                button_notifications.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateMenu();
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
                Intent i = new Intent(DriverMap.this, ChatsActivity.class);
                startActivity(i);
                animateMenu();
            }
        });

        button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DriverMap.this, Profile.class);
                startActivity(i);
                animateMenu();
            }
        });

        button_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateMenu();
                Intent i=new Intent(DriverMap.this, Notifications.class);
                startActivity(i);
            }
        });

        btnRideStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"PENDING TO CODE",Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(DriverMap.this).start();
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

    /**
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     *
     *                             AQUI EMPIEZAN LOS METODOS QUE TIENEN QUE VER CON EL MAPA
     *
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     ***********************************************************************************************************************************/


    private void iniciarMapa() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapDriver);
        mapFragment.getMapAsync(this::onMapReady);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);
        buildGoogleApiClient();
    }

    private LocationManager locationManager;

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    private void showDialogMessageGpsEnable() {

        new MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
                .setTitle("GPS DISABLED")
                .setIcon(R.drawable.ic_baseline_gps_off_24)
                .setMessage("In order to continue, please activate the gps")
                .setCancelable(false)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(DriverMap.this, MainActivity.class));
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
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_CANCELED) {
                            buildGoogleApiClient();
                        }
                    }
                }
        );
    }

    protected synchronized void buildGoogleApiClient() {
        if (isLocationEnabled()) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();
        } else {
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
           if(!getCustomersAroundStarted){
               getAllCustomers();
           }
           LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
           button_ubi.setVisibility(View.VISIBLE);
       }
    }

    private Marker myWork;
    private Marker myHome;

    private void setDestination(){
        DatabaseReference driverReference=FirebaseDatabase.getInstance().getReference("Drivers");
        Map<String,Object> map=new HashMap<>();
        LatLng destination=new LatLng(Double.valueOf(getDestination(Companion.user.getWorkAddress(),"latitude")),Double.valueOf(getDestination(Companion.user.getWorkAddress(),"longitude")));
        if(destination==new LatLng(myLastLocation.getLatitude(),myLastLocation.getLongitude())){
            map.put("destination", Companion.user.getLocalidad());
            map.put("destinationLat",getDestination(Companion.user.getLocalidad(),"latitude"));
            map.put("destinationLong",getDestination(Companion.user.getLocalidad(),"longitude"));
        }else{
            map.put("destination", Companion.user.getWorkAddress());
            map.put("destinationLat",getDestination(Companion.user.getWorkAddress(),"latitude"));
            map.put("destinationLong",getDestination(Companion.user.getWorkAddress(),"longitude"));
        }
        driverReference.child(firebaseAuth.getUid()).child("destinations").updateChildren(map);
        setMarkers();

    }

    private void setMarkers(){
        LatLng myDestination=new LatLng(Double.valueOf(getDestination(Companion.user.getWorkAddress(),"latitude")),Double.valueOf(getDestination(Companion.user.getWorkAddress(),"longitude")));
        CircleOptions circleOptions=new CircleOptions()
                .center(myDestination)
                .radius(1500)
                .strokeColor(getColor(R.color.secondary))
                .clickable(false)
                .strokeWidth(7);
        circleMap=mMap.addCircle(circleOptions);
        myWork=mMap.addMarker(new MarkerOptions().position(myDestination).title("My work").icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(getDrawable(R.drawable.work))))
                .flat(true).anchor(0.5f,0.5f));

        LatLng myHomeLatLng=new LatLng(Double.valueOf(getDestination(Companion.user.getLocalidad(),"latitude")),Double.valueOf(getDestination(Companion.user.getLocalidad(),"longitude")));
        myHome=mMap.addMarker(new MarkerOptions().position(myHomeLatLng).title("My home").icon(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(getDrawable(R.drawable.home))))
                .flat(true).anchor(0.5f,0.5f));
    }

    private void setMyLocationInDatabase(Location location){
        String userId=firebaseAuth.getUid();
        DatabaseReference driverReference=FirebaseDatabase.getInstance().getReference("locationUpdates").child("Drivers");
        GeoFire geofireRef=new GeoFire(driverReference);
        geofireRef.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));
    }

    private Double getDestination(String destination,String field){
        Geocoder geocoder = new Geocoder(DriverMap.this, Locale.getDefault());
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
     *                             AQUI TERMINAN LOS METODOS QUE TIENEN QUE VER CON EL MAPA
     *
     * *********************************************************************************************************************************
     * *********************************************************************************************************************************
     ***********************************************************************************************************************************/

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if(marker!=null&&marker.getTag()!=null){
            erasePolylines();
            if(!marker.getTag().toString().equals(firebaseAuth.getUid())&&(marker.getTag()!=null||marker.getTag().toString().equals(""))){
                String customerUid=marker.getTag().toString();
                customerLatLng= marker.getPosition();
                getCustomerDestination(customerUid);
            }
        }
        return false;
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
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
                    txt_travelInformation.setText("You will arrive in " + route.get(i).getDurationText());
                    txt_distance.setText("You are " + route.get(i).getDistanceText() + " away");
                }
            }
        });

    }

    @Override
    public void onRoutingCancelled() {

    }

    private void erasePolylines() {

        if(markerFinal!=null){
            markerFinal.remove();
        }
        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();

        for(Polyline line:polylines2){
            line.remove();
        }
        polylines2.clear();

        if (locationCallback != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }
}
