package com.vincentaitzz.bettermanagement.ViewModel;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth auth;

    public LoginViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    // Método para iniciar sesión usando Firebase Authentication
    public void login(String email, String password, OnCompleteListener<AuthResult> listener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener);
    }
}