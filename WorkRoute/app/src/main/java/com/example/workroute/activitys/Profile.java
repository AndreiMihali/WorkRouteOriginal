package com.example.workroute.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.workroute.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    TextView userName;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Register);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        main();
    }

    private void main() {
        controls();
        back();
        getUser();
    }

    private void controls() {
        toolbar=findViewById(R.id.toolbar);
        userName = findViewById(R.id.name);
    }

    private void getUser() {
        //Se coge el email de usuario de la cuenta, ya sea google o email
        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //Con getDisplayName no coge el nombre de usuario si no es de cuenta de google
        //habría que mandarlo con preferencias.
    }



    private void back() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar al main
                finish();

            }
        });
    }
}