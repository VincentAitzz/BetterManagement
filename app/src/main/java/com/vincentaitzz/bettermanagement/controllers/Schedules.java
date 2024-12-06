package com.vincentaitzz.bettermanagement.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.helpers.FirebaseManager;
import com.vincentaitzz.bettermanagement.helpers.ScheduleAdapter;
import com.vincentaitzz.bettermanagement.models.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Schedules extends AppCompatActivity {

    private RecyclerView scheduleRecycler;
    private Button addScheduleButton;
    private String currentUserID;
    private List<Schedule> schedules;
    private ScheduleAdapter adapter;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private FirebaseManager auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.schedules);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = new FirebaseManager();
        schedules = new ArrayList<>();
        adapter = new ScheduleAdapter(this, schedules);

        if (!auth.isUserSignedIn()){
            Intent i = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(i);
            finish();
            return;
        }

        drawerLayout = findViewById(R.id.main);
        scheduleRecycler = findViewById(R.id.schedule_recycler);
        addScheduleButton = findViewById(R.id.add_schedule_button);
        navigationView = findViewById(R.id.nav_view);

        currentUserID = auth.getCurrentUser().getUid();

        scheduleRecycler.setLayoutManager(new LinearLayoutManager(this));
        scheduleRecycler.setAdapter(adapter);

        addScheduleButton.setOnClickListener(v -> {
            Schedule newSchedule = new Schedule("1", "2024-12-01", "09:00", "10:00", "Reunión", "Descripción");
            newSchedule.createSchedule(currentUserID);
            schedules.add(newSchedule);
            adapter.notifyDataSetChanged();
        });

        readSchedules();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home) {
                    startActivity(new Intent(Schedules.this, Home.class));
                } else if (item.getItemId() == R.id.nav_shedules) {
                    startActivity(new Intent(Schedules.this, Schedules.class));
                    finish();
                } else if (item.getItemId() == R.id.nav_infoUser) {
                    startActivity(new Intent(Schedules.this, InfoUser.class));
                } else if (item.getItemId() == R.id.nav_logOut) {
                    auth.signOut();
                    startActivity(new Intent(Schedules.this, SignIn.class));
                    finish();
                } else {
                    return false;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }

    private void readSchedules() {
        Schedule scheduleModel = new Schedule();
        scheduleModel.readSchedules(currentUserID, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                schedules.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Schedule schedule = snapshot.getValue(Schedule.class);
                    schedules.add(schedule);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}