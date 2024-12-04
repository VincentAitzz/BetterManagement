package com.vincentaitzz.bettermanagement.controllers;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vincentaitzz.bettermanagement.R;

public class PageTwo extends Fragment {

    private Button btnRegister;

    public PageTwo() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_two, container, false);

        btnRegister = view.findViewById(R.id.goToRegister);

        btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), RegisterInApp.class);
            startActivity(i);
        });
        return view;
    }
}