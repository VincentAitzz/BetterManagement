package com.vincentaitzz.bettermanagement.controllers;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vincentaitzz.bettermanagement.models.User;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class FirebaseManager {

    private FirebaseAuth auth;
    private DatabaseReference dbReference;

    public FirebaseManager(){
        auth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference("users");
    }
    public void signIn(User userData, OnCompleteListener<AuthResult> listener){
        auth.signInWithEmailAndPassword(userData.getEMAIL(), userData.getPASSWORD()).addOnCompleteListener(listener);
    }
    public void signUp(User userData, OnCompleteListener<AuthResult> listener){
        String hashPWD = BCrypt.withDefaults().hashToString(15,userData.getPASSWORD().toCharArray());
        auth.createUserWithEmailAndPassword(userData.getEMAIL(),hashPWD).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseUser firebaseUser = auth.getCurrentUser();
                String userID = firebaseUser.getUid();

                userData.setPASSWORD(hashPWD);

                dbReference.child(userID).setValue(userData).addOnCompleteListener(dbTask -> {
                    if(dbTask.isSuccessful()){
                        listener.onComplete(task);
                    }else{
                        listener.onComplete(Tasks.forException(dbTask.getException()));
                    }
                });
            }else{
                listener.onComplete(task);
            }
        });
    }

    public void signOut(){
        auth.signOut();
    }
    public FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    public boolean isUserSignedIn(){
        return auth.getCurrentUser() != null;
    }
}
