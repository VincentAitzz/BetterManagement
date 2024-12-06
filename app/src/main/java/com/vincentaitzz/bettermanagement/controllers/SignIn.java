package com.vincentaitzz.bettermanagement.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.helpers.FirebaseManager;
import com.vincentaitzz.bettermanagement.models.User;

public class SignIn extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private TextView txtException;
    private Button btnSignIn;
    private FirebaseManager auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = new FirebaseManager();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(v -> {
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Por favor, complete todos los campos",Toast.LENGTH_SHORT).show();
                return;
            }

            User userData = new User();
            userData.setEMAIL(email);
            userData.setPASSWORD(password);


            auth.signIn(userData, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(SignIn.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                    Toast.makeText(SignIn.this, "Error al iniciar sesión: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
