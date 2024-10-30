package com.vincentaitzz.bettermanagement.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.vincentaitzz.bettermanagement.R;

public class FormLogin extends AppCompatActivity {

    private ViewPager2 viewPager;
    private PagerAdapter adapter;
    private Button btnPrev, btnNext;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.form_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.formlogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.viewPager);
        adapter = new PagerAdapter(this);
        viewPager.setAdapter(adapter);

        btnPrev = findViewById(R.id.buttonPrev);
        btnNext = findViewById(R.id.buttonNext);

        btnPrev.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0){
                viewPager.setCurrentItem(currentItem -1);
            }
        });

        btnNext.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();

            if (currentItem == 1) {
                PageTwoFragment currentFragment = (PageTwoFragment) adapter.createFragment(currentItem);

                if (currentFragment.isNameEmpty()) {
                    Toast.makeText(this, "Por favor, ingresa tu nombre.", Toast.LENGTH_SHORT).show();
                } else {
                    viewPager.setCurrentItem(currentItem + 1);
                }
            } else {
                if (currentItem < adapter.getItemCount() - 1) {
                    viewPager.setCurrentItem(currentItem + 1);
                }
            }
        });
    }
}