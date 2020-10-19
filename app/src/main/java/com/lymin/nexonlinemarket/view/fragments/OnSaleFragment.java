package com.lymin.nexonlinemarket.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lymin.nexonlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnSaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnSaleFragment extends Fragment {

    public OnSaleFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static OnSaleFragment newInstance() {
        OnSaleFragment fragment = new OnSaleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_sale, container, false);
    }
}