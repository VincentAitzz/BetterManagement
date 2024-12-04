package com.vincentaitzz.bettermanagement.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vincentaitzz.bettermanagement.R;

public class SplashScreen extends AppCompatActivity {

    private Button btnGetStarted;
    private TextView signInText;
    private FirebaseManager auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = new FirebaseManager();

        if (auth.isUserSignedIn()){
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            finish();
            return;
        }

        btnGetStarted = findViewById(R.id.getStartedButton);
        signInText = findViewById(R.id.signInText);

        btnGetStarted.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), FormLogin.class);
            startActivity(i);
            finish();
        });

        signInText.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), SignIn.class);
            startActivity(i);
            finish();
        });
    }
}