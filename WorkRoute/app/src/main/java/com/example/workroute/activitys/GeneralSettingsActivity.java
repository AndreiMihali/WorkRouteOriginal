package com.example.workroute.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workroute.R;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import yuku.ambilwarna.AmbilWarnaDialog;

public class GeneralSettingsActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private SwitchMaterial appBox;
    private TextView tv_HowToUseWorkRoute;
    private TextView tv_AboutUs;
    private Slider sliderWorkRadius, sliderSearch;
    private MaterialCardView cardColorRadius, cardColorWork, cardColorHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);
        new NetworkCallback().enable(this);
        main();
    }

    private void main() {
        controls();
        initListeners();
    }

    private void controls() {
        toolbar = findViewById(R.id.toolbarSettings);
        tv_HowToUseWorkRoute = findViewById(R.id.tvHowToUseWorkRoute);
        tv_AboutUs = findViewById(R.id.tvAboutUs);
        appBox = findViewById(R.id.switchApp);
        sliderWorkRadius = findViewById(R.id.workRadiusSlider);
        sliderSearch = findViewById(R.id.searchRadius);
        cardColorRadius = findViewById(R.id.colorRadius);
        cardColorHome = findViewById(R.id.colorHomeTrack);
        cardColorWork = findViewById(R.id.colorWorkTrack);
        appBox.setChecked(getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).getBoolean("notificationsApp", true));
        sliderWorkRadius.setValue(getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).getFloat("workRadius", 1.5f));
        sliderSearch.setValue(getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).getFloat("searchRadius", 30f));
        cardColorRadius.setCardBackgroundColor(getSharedPreferences(getString(R.string.sharedPreferences),Context.MODE_PRIVATE).getInt("radiusColor",getColor(R.color.secondary)));
        cardColorHome.setCardBackgroundColor(getSharedPreferences(getString(R.string.sharedPreferences),Context.MODE_PRIVATE).getInt("homeColor",getColor(R.color.secondary)));
        cardColorWork.setCardBackgroundColor(getSharedPreferences(getString(R.string.sharedPreferences),Context.MODE_PRIVATE).getInt("workColor",getColor(R.color.secondaryLine)));
    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        cardColorRadius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorSheet(cardColorRadius,"Radius");
            }
        });

        cardColorHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorSheet(cardColorHome,"Home");
            }
        });

        cardColorWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorSheet(cardColorWork,"Work");
            }
        });

        appBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (appBox.isChecked()) {
                    getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit()
                            .putBoolean("notificationsApp", true).commit();

                } else {
                    getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit()
                            .putBoolean("notificationsApp", false).commit();

                }

            }
        });

        sliderSearch.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if (fromUser) {
                    getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit()
                            .putFloat("searchRadius", value).commit();
                }
            }
        });

        sliderWorkRadius.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if (fromUser) {
                    getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit()
                            .putFloat("workRadius", value).commit();
                }
            }
        });

        tv_HowToUseWorkRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralSettingsActivity.this, HelpCenter.class));
            }
        });

        tv_AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralSettingsActivity.this, AppInformation.class));
            }
        });

    }

    private void openColorSheet(MaterialCardView card, String colorAttribute) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, getColor(R.color.secondary), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                switch (colorAttribute) {
                    case "Radius":
                        getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit().putInt("radiusColor", color).commit();
                        break;
                    case "Home":
                        getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit().putInt("homeColor", color).commit();
                        break;
                    case "Work":
                        getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE).edit().putInt("workColor", color).commit();
                        break;
                }
                card.setCardBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    @Override
    protected void onDestroy() {
        new MainActivity.Destroy(GeneralSettingsActivity.this).start();
        super.onDestroy();
    }
}