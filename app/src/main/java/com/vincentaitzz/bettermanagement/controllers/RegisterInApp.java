package com.vincentaitzz.bettermanagement.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.models.User;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class RegisterInApp extends AppCompatActivity {

    private EditText nameInput,pwdInput,emailInput,confirmPwdInput;
    private Button registerButton;
    private ImageButton backButton;
    private User userData;
    private FirebaseManager auth;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_in_app);
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

        nameInput = findViewById(R.id.username_input);
        pwdInput = findViewById(R.id.password_input);
        confirmPwdInput = findViewById(R.id.confirm_password_input);
        emailInput = findViewById(R.id.email_input);

        registerButton = findViewById(R.id.register_button);
        backButton = findViewById(R.id.back_button);

        registerButton.setOnClickListener(v -> {
            String pwd = pwdInput.getText().toString();
            String cpwd = confirmPwdInput.getText().toString();

            String name = nameInput.getText().toString();
            String email = emailInput.getText().toString();

            User user = new User(null,name,pwd,email);

            if(pwd.equals(cpwd)){
                auth.signUp(user,task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Registro Exitoso.",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),Home.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(), "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}