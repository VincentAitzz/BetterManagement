package com.vincentaitzz.bettermanagement.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.helpers.FirebaseManager;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private FirebaseManager auth;
    private NavigationView navigationView;

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

        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);
        auth = new FirebaseManager();

        if (!auth.isUserSignedIn()){
            Intent i = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(i);
            finish();
            return;
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(Home.this, Home.class));
                    finish();
                } else if (item.getItemId() == R.id.nav_shedules) {
                    startActivity(new Intent(Home.this, Schedules.class));
                } else if (item.getItemId() == R.id.nav_msg) {
                    startActivity(new Intent(Home.this, Chat.class));
                }else if (item.getItemId() == R.id.nav_infoUser) {
                    startActivity(new Intent(Home.this, InfoUser.class));
                } else if (item.getItemId() == R.id.nav_logOut) {
                    auth.signOut();
                    startActivity(new Intent(Home.this, SignIn.class));
                    finish();
                } else {
                    return false;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
}