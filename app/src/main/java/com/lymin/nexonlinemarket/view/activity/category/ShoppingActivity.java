package com.lymin.nexonlinemarket.view.activity.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.adapter.AdapterShopProductCard;
import com.lymin.nexonlinemarket.databinding.ActivityShoppingBinding;
import com.lymin.nexonlinemarket.managers.UsersManager;
import com.lymin.nexonlinemarket.model.SaleItem;
import com.lymin.nexonlinemarket.user.UsersModel;
import com.lymin.nexonlinemarket.user.UsersRealm;
import com.lymin.nexonlinemarket.utils.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class ShoppingActivity extends AppCompatActivity {

    private ActivityShoppingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shopping);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        String cat = getIntent().getStringExtra("cat");
        getData(cat);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Tools.setSystemBarColor(this, R.color.grey_10);
        Tools.setSystemBarLight(this);
    }

    private void getData(String cat) {
        final List<SaleItem> list =  new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Products").whereEqualTo("categoryID",cat)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            SaleItem saleItem = document.toObject(SaleItem.class);
                            list.add(saleItem);
                        }

                        binding.recyclerView.setAdapter(new AdapterShopProductCard(ShoppingActivity.this,list));
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

    }
}