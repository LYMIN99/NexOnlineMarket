package com.lymin.nexonlinemarket.view.activity.uploadSale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.adapter.AddPhotoAdapter;
import com.lymin.nexonlinemarket.databinding.ActivityNewSaleBinding;
import com.lymin.nexonlinemarket.model.PhotoUpload;

import java.util.ArrayList;
import java.util.List;

public class NewSaleActivity extends AppCompatActivity {


    private ActivityNewSaleBinding binding;
    private List<PhotoUpload> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_new_sale);

        list = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.recyclerView.setAdapter(new AddPhotoAdapter(this,list));

    }
}