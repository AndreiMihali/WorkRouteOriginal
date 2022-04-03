package com.example.workroute.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.workroute.R;
import com.example.workroute.activitys.DrivingLicense;
import com.example.workroute.activitys.PayMethod;
import com.example.workroute.activitys.Profile;


public class CuentaFragment extends Fragment {

    private TextView drivingLicense;
    private TextView payMethods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);
        drivingLicense = view.findViewById(R.id.myDrivingLicenseButton);
        payMethods = view.findViewById(R.id.myPayMethodButton);

        main();


        // Inflate the layout for this fragment
        return view;
    }



    private void main() {
      listeners();

    }



    private void listeners() {

        drivingLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openDrivingLicense = new Intent(getActivity(), DrivingLicense.class);
                startActivity(openDrivingLicense);
            }
        });

        payMethods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPayMethods = new Intent(getActivity(), PayMethod.class);
                startActivity(openPayMethods);
            }
        });

    }






}