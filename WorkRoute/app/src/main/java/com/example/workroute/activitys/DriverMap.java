package com.example.workroute.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.workroute.companion.Companion;
import com.example.workroute.kotlin.activities.ChatsActivity;
import com.example.workroute.kotlin.activities.MessagesActivity;
import com.example.workroute.model.User;
import com.example.workroute.network.callback.NetworkCallback;
import com.example.workroute.service.ServicioOnline;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DriverMap extends FragmentActivity implements com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    private FloatingActionButton button_menu, button_chats, button_profile, button_notifications, button_ubi, button_customer, button_driver;
    private Animation open, close, rotateForward, rotateBackWard;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private boolean isOpen = false;
    private boolean isOpen2 = false;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
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
    private static final int[] COLORS = new int[]{R.color.secondary};

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
        getToken();
        init();
        getAssignedCustomer();
        startService(new Intent(this, ServicioOnline.class));

    }

    private void getAssignedCustomer() {
        String driverId = firebaseAuth.getUid();
        DatabaseReference assignedCustomerId = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(driverId).child("customerRequest").child("customerRideId");
        assignedCustomerId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    customerId = snapshot.getValue().toString();
                    getAssignedCustomerLocation();
                    getAssignedCustomerDestination();
                } else {
                    erasePolylines();
                    customerId = "";
                    if (pickupMarker != null) {
                        pickupMarker.remove();
                    }
                    if (assignedCustomerPickUpLocationRefListener != null) {
                        assignedCustomerPickUpLocationRef.removeEventListener(assignedCustomerPickUpLocationRefListener);
                    }
                    displayInformationCustomer(BottomSheetBehavior.STATE_HIDDEN);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAssignedCustomerDestination() {
        String driverId = firebaseAuth.getUid();
        DatabaseReference assignedCustomerId = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(driverId).child("customerRequest").child("destination");
        assignedCustomerId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String destination = snapshot.getValue().toString();
                    txt_destination.setText(destination);
                } else {
                    txt_destination.setText("--");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getAssignedCustomerInfo() {
        displayInformationCustomer(BottomSheetBehavior.STATE_EXPANDED);
        DatabaseReference mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(customerId);
        mCustomerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if (map.get("nombre") != null) {
                        txt_name.setText("Your customer is " + map.get("nombre").toString());
                    }

                    if (map.get("fotoPerfil") != null) {
                        if (map.get("fotoPerfil").toString().equals("")) {
                            customer_photo.setImageResource(R.drawable.default_user_login);
                        } else {
                            Glide.with(getApplicationContext()).load(map.get("fotoPerfil").toString()).into(customer_photo);
                        }
                    }

                    btn_message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DriverMap.this, MessagesActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .putExtra("userId", customerId)
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

    private void displayInformationCustomer(int state) {
        txt_distance = findViewById(R.id.txt_distance);
        customer_photo = findViewById(R.id.profile_photo_sheet);
        txt_name = findViewById(R.id.txt_name);
        txt_travelInformation = findViewById(R.id.txt_travel_information);
        txt_total_pay_travel = findViewById(R.id.txt_total_pay_travel);
        txt_startLocation = findViewById(R.id.txt_startLocation);
        txt_destination = findViewById(R.id.txt_destination);

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

    private Marker pickupMarker;
    private DatabaseReference assignedCustomerPickUpLocationRef;
    private ValueEventListener assignedCustomerPickUpLocationRefListener;
    private LatLng driverLatLng;

    private void getAssignedCustomerLocation() {
        assignedCustomerPickUpLocationRef = FirebaseDatabase.getInstance().getReference().child("customerRequest").child(customerId).child("l");
        assignedCustomerPickUpLocationRefListener = assignedCustomerPickUpLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && !customerId.equals("")) {
                    List<Object> map = (List<Object>) snapshot.getValue();
                    double locationLat = 0;
                    double locationLong = 0;
                    if (map.get(0) != null) {
                        locationLat = Double.parseDouble(map.get(0).toString());
                    }
                    if (map.get(1) != null) {
                        locationLong = Double.parseDouble(map.get(1).toString());
                    }
                    driverLatLng = new LatLng(locationLat, locationLong);
                    pickupMarker = mMap.addMarker(new MarkerOptions().position(driverLatLng).title("Pickup location")
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_pin_map_foreground)));
                    getAssignedCustomerInfo();
                    getRouteToMarker(driverLatLng);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getRouteToMarker(LatLng pickupMarker) {
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(new LatLng(myLastLocation.getLatitude(), myLastLocation.getLongitude()), pickupMarker)
                .key(getString(R.string.google_maps_key))
                .build();
        routing.execute();
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        setAvailable(myLastLocation);
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
        button_menu = findViewById(R.id.buttonMenu);
        button_chats = findViewById(R.id.buttonMessages);
        button_profile = findViewById(R.id.buttonProfile);
        button_notifications = findViewById(R.id.button_notifications);
        //button_settings = findViewById(R.id.buttonSettings);
        button_ubi = findViewById(R.id.buttonUbi);
        button_customer = findViewById(R.id.buttonCustomer);
        button_driver = findViewById(R.id.buttonDriver);
        open = AnimationUtils.loadAnimation(this, R.anim.open_menu);
        close = AnimationUtils.loadAnimation(this, R.anim.close_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
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
        iniciarMapa();
        getUserData();
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

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateMenu();
            }
        });

        button_ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateMenu2();
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

        button_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void animateMenu2() {
        if (isOpen2) {
            button_driver.setVisibility(View.INVISIBLE);
            button_customer.setVisibility(View.INVISIBLE);
            button_driver.setClickable(false);
            button_customer.setClickable(false);
            isOpen2 = false;
        } else {
            button_driver.setVisibility(View.VISIBLE);
            button_customer.setVisibility(View.VISIBLE);
            button_driver.setClickable(true);
            button_customer.setClickable(true);
            isOpen2 = true;
        }
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


    private void iniciarMapa() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapDriver);
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
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (getApplicationContext() != null) {
            myLastLocation = location;
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            setAvailable(location);
        }
    }

    private void setAvailable(Location location) {
        String userId = firebaseAuth.getUid();
        DatabaseReference referenceAvailable = FirebaseDatabase.getInstance().getReference("driverAvailable");
        DatabaseReference refWorking = FirebaseDatabase.getInstance().getReference("driverWorking");
        GeoFire geofireReference = new GeoFire(referenceAvailable);
        GeoFire geofireWorking = new GeoFire(refWorking);

        switch (customerId) {
            case "":
                geofireWorking.removeLocation(userId);
                geofireReference.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
                break;
            default:
                geofireReference.removeLocation(userId);
                geofireWorking.setLocation(userId, new GeoLocation(location.getLatitude(), location.getLongitude()));
                break;
        }
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

        String userId = firebaseAuth.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driverAvailable");

        GeoFire geofire = new GeoFire(reference);
        geofire.removeLocation(userId);

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

    private void getUserData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                firestore.collection("Usuarios").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Companion.user = documentSnapshot.toObject(User.class);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ERROR AL OBTENER LOS DATOS DEL USUARIO", "ERROR AL OBTENER LOS DATOS DEL USUARIO " + e);
                    }
                });
            }
        });
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
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
        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {
            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(25 + i * 20);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            txt_travelInformation.setText("You will arrive in " + route.get(i).getDurationText());
            txt_distance.setText("You are " + route.get(i).getDistanceText() + " away");
        }

    }

    @Override
    public void onRoutingCancelled() {

    }

    private void erasePolylines() {
        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();

        if (locationCallback != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }
}
