package com.lymin.nexonlinemarket.view.activity.profile.sub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lymin.nexonlinemarket.R;

public class SellingFragment extends Fragment {
    public SellingFragment() {
        // Required empty public constructor
    }
    public static SellingFragment newInstance() {
        return new SellingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selling, container, false);
    }
}