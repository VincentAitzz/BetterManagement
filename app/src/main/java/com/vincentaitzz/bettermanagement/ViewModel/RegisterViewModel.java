package com.vincentaitzz.bettermanagement.ViewModel;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vincentaitzz.bettermanagement.Model.User;
import com.vincentaitzz.bettermanagement.Model.UserRepo;

public class RegisterViewModel extends ViewModel {

    private FirebaseAuth auth;
    private UserRepo userRepo;

    public RegisterViewModel(){
        auth = FirebaseAuth.getInstance();
        userRepo = new UserRepo();
    }

    public void register(String email, String password, String nombre, OnCompleteListener<AuthResult> listener) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // El registro fue exitoso
                FirebaseUser firebaseUser = auth.getCurrentUser();
                // Aquí puedes decidir si guardar el usuario en Realtime Database o no
                // Por ejemplo: User user = new User(firebaseUser.getUid(), nombre, email);
                // userRepository.saveUser(user); // Guardar en Realtime Database
            }
            listener.onComplete(task);
        });
    }
}
