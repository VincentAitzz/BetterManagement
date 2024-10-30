package com.vincentaitzz.bettermanagement.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.vincentaitzz.bettermanagement.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class PageTwoFragment extends Fragment {

    private EditText editTextName;

    @NonNull
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_two, container, false);
        editTextName = view.findViewById(R.id.editTextName);
        return view;
    }

    public String getName() {
        return editTextName.getText().toString().trim();
    }

    public boolean isNameEmpty() {
        return getName().isEmpty();
    }
}