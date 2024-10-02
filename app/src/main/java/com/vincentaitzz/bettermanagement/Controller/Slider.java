package com.vincentaitzz.bettermanagement.Controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vincentaitzz.bettermanagement.R;

public class Slider extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static Slider newInstance(int layoutResId) {
        Slider fragment = new Slider();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            int layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
            return inflater.inflate(layoutResId, container, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}