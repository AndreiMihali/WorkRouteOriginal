package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.workroute.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton b1,b2,b3;
    Animation open,close,rotateForward,rotateBackWard;

    boolean isOpen = false;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main();
        
        
    }

    private void main() {
        controls();
        listeners();
    }



    private void controls() {
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);

        open = AnimationUtils.loadAnimation(this,R.anim.open_menu);
        close = AnimationUtils.loadAnimation(this,R.anim.close_menu);
        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackWard = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);
    }

    private void listeners() {
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateMenu();
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"Saludos del menu 22222", Toast.LENGTH_SHORT).show();
                }
            });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"Saludos del menu 333", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void animateMenu() {
        if (isOpen) {
            b1.startAnimation(rotateForward);
            b2.startAnimation(close);
            b3.startAnimation(close);
            b2.setClickable(false);

            b2.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.INVISIBLE);

            b2.setClickable(false);
            b3.setClickable(false);
            isOpen=false;
        } else {
            b1.startAnimation(rotateBackWard);
            b2.startAnimation(open);
            b3.startAnimation(open);

            b2.setVisibility(View.VISIBLE);
            b3.setVisibility(View.VISIBLE);

            b2.setClickable(true);
            b3.setClickable(true);
            isOpen=true;
        }
    }
}