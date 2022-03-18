package com.example.workroute.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.workroute.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private FloatingActionButton fab;
    private GoogleMap map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        SupportMapFragment mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fab=view.findViewById(R.id.fb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawRoute();
            }
        });
        return view;
    }

    private void drawRoute() {
        LatLng place=new LatLng(51.23907802383949, -0.7065019752066959);
        LatLng place2=new LatLng(55.847555, 36.085491);
        PolylineOptions options=new PolylineOptions().clickable(true);
        options.color(R.color.secondary);
        options.add(place,place2);
        map.addPolyline(options);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        createMarker();
    }

    private void createMarker() {
        LatLng place=new LatLng(51.23907802383949, -0.7065019752066959);
        map.addMarker(new MarkerOptions().position(place).title("China"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(place,18f),4000,null);
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                return true;
            }
        });
    }
}