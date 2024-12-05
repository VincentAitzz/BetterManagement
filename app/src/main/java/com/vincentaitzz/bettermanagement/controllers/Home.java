package com.vincentaitzz.bettermanagement.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vincentaitzz.bettermanagement.R;

public class Home extends AppCompatActivity {

    private Button btn;
    private FirebaseManager auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn = findViewById(R.id.btnLogOut);
        auth = new FirebaseManager();

        btn.setOnClickListener(v -> {
            auth.signOut();
            Intent i = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(i);
            finish();
        });
    }
}