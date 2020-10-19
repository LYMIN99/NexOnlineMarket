package com.lymin.nexonlinemarket.view.activity.uploadSale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.adapter.AdapterListShopCategory;
import com.lymin.nexonlinemarket.adapter.CategoriesAdapter;
import com.lymin.nexonlinemarket.model.Categories;
import com.lymin.nexonlinemarket.utils.DataGenerator;
import com.lymin.nexonlinemarket.view.activity.HomeActivity;
import com.lymin.nexonlinemarket.view.activity.ShopCategory;
import com.lymin.nexonlinemarket.view.activity.category.ShoppingActivity;

import java.util.List;

public class ChooseCategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_categories);
        initComponent();
    }

    private void initComponent() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        List<Categories> items = DataGenerator.getCategories(this);

        //set data and list adapter
        CategoriesAdapter mAdapter = new CategoriesAdapter(this, items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Categories obj, int position) {
                startActivity(new Intent(ChooseCategoriesActivity.this, NewSaleActivity.class));
            }
        });

    }
}