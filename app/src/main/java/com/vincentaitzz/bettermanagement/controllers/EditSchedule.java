package com.vincentaitzz.bettermanagement.controllers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.helpers.FirebaseManager;
import com.vincentaitzz.bettermanagement.models.Schedule;

public class EditSchedule extends AppCompatActivity {

    private EditText txtName, txtDescription, txtDate, txtStart, txtFinish;
    private Button btnSaveChanges;

    private Schedule currentSchedule;
    private FirebaseManager auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.edit_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = new FirebaseManager();

        txtName = findViewById(R.id.edit_schedule_name);
        txtDescription = findViewById(R.id.edit_schedule_description);
        txtDate = findViewById(R.id.edit_schedule_date);
        txtStart = findViewById(R.id.edit_schedule_start);
        txtFinish = findViewById(R.id.edit_schedule_finish);
        btnSaveChanges = findViewById(R.id.button_save_changes);

        String scheduleId = getIntent().getStringExtra("SCHEDULE_ID");
        String scheduleName = getIntent().getStringExtra("SCHEDULE_NAME");
        String scheduleDate = getIntent().getStringExtra("SCHEDULE_DATE");
        String scheduleStart = getIntent().getStringExtra("SCHEDULE_START");
        String scheduleFinish = getIntent().getStringExtra("SCHEDULE_FINISH");
        String scheduleDescription = getIntent().getStringExtra("SCHEDULE_DESCRIPTION");

        txtName.setText(scheduleName);
        txtDescription.setText(scheduleDescription);
        txtDate.setText(scheduleDate);
        txtStart.setText(scheduleStart);
        txtFinish.setText(scheduleFinish);

        btnSaveChanges.setOnClickListener(v -> {
            currentSchedule = new Schedule(scheduleId,
                    txtDate.getText().toString(),
                    txtStart.getText().toString(),
                    txtFinish.getText().toString(),
                    txtName.getText().toString(),
                    txtDescription.getText().toString());
            currentSchedule.updateSchedule(auth.getCurrentUser().getUid());
            finish();
        });
    }
}