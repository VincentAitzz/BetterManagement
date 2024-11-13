package com.vincentaitzz.bettermanagement.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vincentaitzz.bettermanagement.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PageTwoFragment extends Fragment {

    private Button btnRegister;
    private Button btnRegisterGoogle;

    @NonNull
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_two, container, false);

        btnRegister = view.findViewById(R.id.goToRegister);
        btnRegisterGoogle = view.findViewById(R.id.buttonGoogleRegister);

        btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), RegisterInApp.class);
            startActivity(i);
        });
        return view;
    }



}