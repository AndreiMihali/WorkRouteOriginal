package com.example.workroute.network.callback;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.workroute.R;

public class NetworkCallback extends ConnectivityManager.NetworkCallback {

    private final NetworkRequest networkRequest;
    private Activity myActivity;

    public NetworkCallback(){
        networkRequest=new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        showToast();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onLosing(@NonNull Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        showToast();
    }

    public void enable(Activity activity){
        ConnectivityManager connectivityManager=(ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerNetworkCallback(networkRequest,this);
        myActivity=activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void showToast(){

        Toast toast=new Toast(myActivity.getApplicationContext());
        View view=myActivity.getLayoutInflater().inflate(R.layout.network_lose,null);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
        toast.addCallback(new Toast.Callback() {
            @Override
            public void onToastHidden() {
                super.onToastHidden();
                myActivity.finish();
            }
        });

    }

}
