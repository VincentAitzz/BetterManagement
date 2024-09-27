package com.vincentaitzz.bettermanagement.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.ViewModel.RegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterFragment2 extends Fragment {

    private EditText editTextPassword;
    private Button buttonRegistrar;

    private RegisterViewModel registerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register2, container, false);

        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonRegistrar = view.findViewById(R.id.buttonRegistrar);

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        buttonRegistrar.setOnClickListener(v -> registrar());

        return view;
    }

    private void registrar() {
        Bundle args = getArguments();
        if (args != null) {
            String nombre = args.getString("nombre");
            String email = args.getString("email");
            String password = editTextPassword.getText().toString();

            registerViewModel.register(email, password, nombre, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                        // Aquí puedes decidir si guardar el usuario en Realtime Database o no.
                        // Si decides hacerlo:
                        // User user = new User(firebaseUser.getUid(), nombre, email);
                        // userRepository.saveUser(user); // Guardar en Realtime Database

                        // Navegar a MainActivity aquí.
                    } else {
                        Toast.makeText(getContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}