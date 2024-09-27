package com.vincentaitzz.bettermanagement.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepo {
    private DatabaseReference databaseReference;

    public UserRepo() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /*
    public void saveUser(User user) {
        databaseReference.child("usuarios").child(user.getUid()).setValue(user);
    }*/
}
