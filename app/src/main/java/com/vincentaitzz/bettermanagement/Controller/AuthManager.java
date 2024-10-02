package com.vincentaitzz.bettermanagement.Controller;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthManager {

    private FirebaseAuth mAuth;

    public AuthManager(){}

    public void signIn(String email, String password, OnCompleteListener<AuthResult> listener){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(listener);
    }

    public void signOut(String email, String password, OnCompleteListener<AuthResult> listener){
        mAuth.signOut();
    }

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }
}
