package com.vincentaitzz.bettermanagement.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.helpers.FirebaseManager;
import com.vincentaitzz.bettermanagement.helpers.PagerAdapter;

public class FormLogin extends AppCompatActivity {

    private ViewPager2 viewPager;
    private PagerAdapter adapter;
    private Button btnPrev, btnNext;
    private FirebaseManager auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.form_login);
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

        viewPager = findViewById(R.id.viewPager);
        btnPrev = findViewById(R.id.buttonPrev);
        btnNext = findViewById(R.id.buttonNext);
        adapter = new PagerAdapter(this);

        viewPager.setAdapter(adapter);

        btnPrev.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if(currentItem > 0){
                viewPager.setCurrentItem(currentItem - 1);
            }else if(currentItem == 0){
                Intent i = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(i);
            }
        });

        btnNext.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            }
        });


    }
}