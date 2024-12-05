package com.vincentaitzz.bettermanagement.controllers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vincentaitzz.bettermanagement.models.User;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class FirebaseManager {

    private FirebaseAuth auth;
    private DatabaseReference dbReference;

    public FirebaseManager(){
        auth = FirebaseAuth.getInstance();
        dbReference = FirebaseDatabase.getInstance().getReference("users");
    }
    public void signIn(User userData, OnCompleteListener<AuthResult> listener) {
        auth.signInWithEmailAndPassword(userData.getEMAIL(), userData.getPASSWORD()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onComplete(task);
            } else {
                dbReference.orderByChild("email").equalTo(userData.getEMAIL()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                User storedUser = dataSnapshot.getValue(User.class);
                                String storedHash = storedUser.getPASSWORD();

                                if (BCrypt.verifyer().verify(userData.getPASSWORD().toCharArray(), storedHash).verified) {
                                    listener.onComplete(task);
                                } else {
                                    listener.onComplete(Tasks.forException(new Exception("Contraseña incorrecta")));
                                }
                            }
                        } else {
                            listener.onComplete(Tasks.forException(new Exception("Usuario no encontrado")));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onComplete(Tasks.forException(error.toException()));
                    }
                });
            }
        });
    }
    public void signUp(User userData, OnCompleteListener<AuthResult> listener) {
        auth.createUserWithEmailAndPassword(userData.getEMAIL(), userData.getPASSWORD()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                String userID = firebaseUser.getUid();

                String hashPWD = BCrypt.withDefaults().hashToString(15, userData.getPASSWORD().toCharArray());
                userData.setPASSWORD(hashPWD);

                dbReference.child(userID).setValue(userData).addOnCompleteListener(dbTask -> {
                    if (dbTask.isSuccessful()) {
                        listener.onComplete(task);
                    } else {
                        listener.onComplete(Tasks.forException(dbTask.getException()));
                    }
                });
            } else {
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
