package com.vincentaitzz.bettermanagement.Controller;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.vincentaitzz.bettermanagement.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private PageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.viewPager);
        int[] layouts = {R.layout.slideshow_first_page,R.layout.slideshow_second_page};
        adapter = new PageAdapter(this,layouts);

        findViewById(R.id.btnPrevious).setOnClickListener(v -> {
            if (viewPager.getCurrentItem() > 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        findViewById(R.id.btnNext).setOnClickListener(v -> {
            if (viewPager.getCurrentItem() < layouts.length - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
    }
}