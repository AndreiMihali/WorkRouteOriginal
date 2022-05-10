package com.example.workroute.activitys;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workroute.R;
import com.example.workroute.network.callback.NetworkCallback;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class HelpCenter extends AppCompatActivity {

    /*
        floating button menu
        ubication button
        driver map
        customer map
        chat
        profile
        ----
        personal information
        pay method
        active subscriptions
        general settings
        delete account
        log out
        ----
        notifications

     */


    private ImageButton boton1;
    private TextView txt1;
    private ImageButton boton2;
    private TextView txt2;
    private ImageButton boton3;
    private TextView txt3;
    private ImageButton boton4;
    private TextView txt4;
    private ImageButton boton5;
    private TextView txt5;
    private ImageButton boton6;
    private TextView txt6;
    private ImageButton boton7;
    private TextView txt7;
    private ImageButton boton8;
    private TextView txt8;
    private ImageButton boton9;
    private TextView txt9;
    private ImageButton boton10;
    private TextView txt10;
    private ImageButton boton11;
    private TextView txt11;
    private ImageButton boton12;
    private TextView txt12;
    private ImageButton boton13;
    private TextView txt13;


    private MaterialCardView cardPersonalInfo;
    private MaterialCardView cardPayMethod;
    private MaterialCardView cardActiveSubs;
    private MaterialCardView cardGeneralSettings;
    private MaterialCardView cardDeleteAccount;
    private MaterialCardView cardLogOut;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        new NetworkCallback().enable(this);
        main();
    }

    private void main() {
        controls();
        initListeners();
    }

    private void controls() {
        toolbar = findViewById(R.id.toolbarUseWorkRoute);
        boton1=findViewById(R.id.expandir_1);
        txt1=findViewById(R.id.txt_1);
        txt1.setVisibility(View.GONE);

        boton2=findViewById(R.id.expandir_2);
        txt2=findViewById(R.id.txt_2);
        txt2.setVisibility(View.GONE);

        boton3=findViewById(R.id.expandir_3);
        txt3=findViewById(R.id.txt_3);
        txt3.setVisibility(View.GONE);

        boton4=findViewById(R.id.expandir_4);
        txt4=findViewById(R.id.txt_4);
        txt4.setVisibility(View.GONE);

        boton5=findViewById(R.id.expandir_5);
        txt5=findViewById(R.id.txt_5);
        txt5.setVisibility(View.GONE);

        boton6=findViewById(R.id.expandir_6);
        txt6=findViewById(R.id.txt_6);
        txt6.setVisibility(View.GONE);

        cardPersonalInfo = findViewById(R.id.cardViewPersonalInfo);
        boton7=findViewById(R.id.expandir_7);
        txt7=findViewById(R.id.txt_7);
        txt7.setVisibility(View.GONE);

        cardPayMethod = findViewById(R.id.cardViewPayMethod);
        boton8=findViewById(R.id.expandir_8);
        txt8=findViewById(R.id.txt_8);
        txt8.setVisibility(View.GONE);

        cardActiveSubs = findViewById(R.id.cardViewActiveSubs);
        boton9=findViewById(R.id.expandir_9);
        txt9=findViewById(R.id.txt_9);
        txt9.setVisibility(View.GONE);

        cardGeneralSettings = findViewById(R.id.cardViewGeneralSettings);
        boton10=findViewById(R.id.expandir_10);
        txt10=findViewById(R.id.txt_10);
        txt10.setVisibility(View.GONE);

        cardDeleteAccount = findViewById(R.id.cardViewDeleteAccount);
        boton11=findViewById(R.id.expandir_11);
        txt11=findViewById(R.id.txt_11);
        txt11.setVisibility(View.GONE);

        cardLogOut = findViewById(R.id.cardViewLogOut);
        boton12=findViewById(R.id.expandir_12);
        txt12=findViewById(R.id.txt_12);
        txt12.setVisibility(View.GONE);

        boton13=findViewById(R.id.expandir_13);
        txt13=findViewById(R.id.txt_13);
        txt13.setVisibility(View.GONE);

    }

    private void initListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt1.getVisibility()==View.GONE){
                    txt1.setVisibility(View.VISIBLE);
                    txt1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton1.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt1.getVisibility()==View.VISIBLE){
                    txt1.setVisibility(View.GONE);
                    boton1.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt2.getVisibility()==View.GONE){
                    txt2.setVisibility(View.VISIBLE);
                    txt2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton2.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt2.getVisibility()==View.VISIBLE){
                    txt2.setVisibility(View.GONE);
                    boton2.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt3.getVisibility()==View.GONE){
                    txt3.setVisibility(View.VISIBLE);
                    txt3.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton3.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt3.getVisibility()==View.VISIBLE){
                    txt3.setVisibility(View.GONE);
                    boton3.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt4.getVisibility()==View.GONE){
                    txt4.setVisibility(View.VISIBLE);
                    txt4.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton4.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt4.getVisibility()==View.VISIBLE){
                    txt4.setVisibility(View.GONE);
                    boton4.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt5.getVisibility()==View.GONE){
                    txt5.setVisibility(View.VISIBLE);
                    txt5.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton5.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt5.getVisibility()==View.VISIBLE){
                    txt5.setVisibility(View.GONE);
                    boton5.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });


        boton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt6.getVisibility()==View.GONE){
                    txt6.setVisibility(View.VISIBLE);
                    txt6.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton6.setImageResource(R.drawable.bx_chevron_up);
                    cardPersonalInfo.setVisibility(View.VISIBLE);
                    cardPayMethod.setVisibility(View.VISIBLE);
                    cardActiveSubs.setVisibility(View.VISIBLE);
                    cardGeneralSettings.setVisibility(View.VISIBLE);
                    cardDeleteAccount.setVisibility(View.VISIBLE);
                    cardLogOut.setVisibility(View.VISIBLE);
                }else if(txt6.getVisibility()==View.VISIBLE){
                    txt6.setVisibility(View.GONE);
                    boton6.setImageResource(R.drawable.bx_chevron_down);
                    cardPersonalInfo.setVisibility(View.GONE);
                    cardPayMethod.setVisibility(View.GONE);
                    cardActiveSubs.setVisibility(View.GONE);
                    cardGeneralSettings.setVisibility(View.GONE);
                    cardDeleteAccount.setVisibility(View.GONE);
                    cardLogOut.setVisibility(View.GONE);
                }
            }
        });

        boton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt7.getVisibility()==View.GONE){
                    txt7.setVisibility(View.VISIBLE);
                    txt7.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton7.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt7.getVisibility()==View.VISIBLE){
                    txt7.setVisibility(View.GONE);
                    boton7.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt8.getVisibility()==View.GONE){
                    txt8.setVisibility(View.VISIBLE);
                    txt8.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton8.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt8.getVisibility()==View.VISIBLE){
                    txt8.setVisibility(View.GONE);
                    boton8.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt9.getVisibility()==View.GONE){
                    txt9.setVisibility(View.VISIBLE);
                    txt9.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton9.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt9.getVisibility()==View.VISIBLE){
                    txt9.setVisibility(View.GONE);
                    boton9.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt10.getVisibility()==View.GONE){
                    txt10.setVisibility(View.VISIBLE);
                    txt10.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton10.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt10.getVisibility()==View.VISIBLE){
                    txt10.setVisibility(View.GONE);
                    boton10.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt11.getVisibility()==View.GONE){
                    txt11.setVisibility(View.VISIBLE);
                    txt11.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton11.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt11.getVisibility()==View.VISIBLE){
                    txt11.setVisibility(View.GONE);
                    boton11.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt12.getVisibility()==View.GONE){
                    txt12.setVisibility(View.VISIBLE);
                    txt12.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton12.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt12.getVisibility()==View.VISIBLE){
                    txt12.setVisibility(View.GONE);
                    boton12.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

        boton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt13.getVisibility()==View.GONE){
                    txt13.setVisibility(View.VISIBLE);
                    txt13.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_in));
                    boton13.setImageResource(R.drawable.bx_chevron_up);
                }else if(txt13.getVisibility()==View.VISIBLE){
                    txt13.setVisibility(View.GONE);
                    boton13.setImageResource(R.drawable.bx_chevron_down);
                }
            }
        });

    }
}
