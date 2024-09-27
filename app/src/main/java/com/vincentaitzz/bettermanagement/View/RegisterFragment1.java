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

import com.vincentaitzz.bettermanagement.R;
import com.vincentaitzz.bettermanagement.ViewModel.RegisterViewModel;

public class RegisterFragment1 extends Fragment {

    private EditText editTextNombre, editTextEmail;
    private Button buttonSiguiente;

    private RegisterViewModel registerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_register1, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonSiguiente = view.findViewById(R.id.buttonSiguiente);

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        buttonSiguiente.setOnClickListener(v -> siguiente());

        return view;
    }

    private void siguiente(){
        // Validar datos y navegar al siguiente fragmento.
        if (!editTextNombre.getText().toString().isEmpty() && !editTextEmail.getText().toString().isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString("nombre", editTextNombre.getText().toString());
            bundle.putString("email", editTextEmail.getText().toString());

            RegisterFragment2 fragment2 = new RegisterFragment2();
            fragment2.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment2)
                    .addToBackStack(null)
                    .commit();
        }
    }
}