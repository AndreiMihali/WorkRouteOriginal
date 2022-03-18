package com.example.workroute.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.workroute.R;
import com.example.workroute.companion.Companion;
import com.example.workroute.fragments.HomeFragment;
import com.example.workroute.fragments.MessagesFragment;
import com.example.workroute.fragments.NavigationFragment;
import com.example.workroute.fragments.UserFragment;
import com.example.workroute.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView profilePhoto;
    private MaterialCardView card_photo;
    private ImageButton button_notifications;
    private BottomNavigationView bottomNavigationView;
    private User userData;
    private BadgeDrawable badge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDataFromFirebase();
        init();
    }

    private void init(){
        toolbar=findViewById(R.id.appbar_main).findViewById(R.id.toolbar);
        profilePhoto=toolbar.findViewById(R.id.profile_image_toolbar);
        card_photo=toolbar.findViewById(R.id.card_photo);
        button_notifications=toolbar.findViewById(R.id.button_notifications);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        replaceFragment(new HomeFragment());
        initListeners();
    }

    private void setData() {
        if(userData.getFotoPerfil().equals("")){
            profilePhoto.setImageDrawable(getDrawable(R.drawable.default_user_login));
            Companion.user=userData;
        }else{
            Glide.with(this).load(userData.getFotoPerfil()).into(profilePhoto);
        }
    }

    private void initListeners() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        return true;
                    case R.id.journeys:
                        replaceFragment(new NavigationFragment());
                        return true;
                    case R.id.messages:
                        replaceFragment(new MessagesFragment());
                        return true;
                    case R.id.profile:
                        replaceFragment(new UserFragment());
                        return true;
                    default:
                        return false;
                }
            }
        });

        button_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menur_bottom_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragManager.beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();
    }

    private void loadDataFromFirebase(){
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore firestore=FirebaseFirestore.getInstance();
            firestore.collection("Usuarios").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    userData =documentSnapshot.toObject(User.class);
                    setData();
                }
            });
    }

}