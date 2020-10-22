package com.lymin.nexonlinemarket.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.adapter.AdapterShopProductCard;
import com.lymin.nexonlinemarket.adapter.MySaleItemAdapter;
import com.lymin.nexonlinemarket.model.SaleItem;
import com.lymin.nexonlinemarket.view.activity.category.ShoppingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

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
        return new OnSaleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_on_sale, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();
        return view;
    }

    private void getData() {
        final List<SaleItem> list =  new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        db.collection("Products").whereEqualTo("sellerID",firebaseUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            SaleItem saleItem = document.toObject(SaleItem.class);
                            list.add(saleItem);
                        }

                        recyclerView.setAdapter(new MySaleItemAdapter(getContext(),list));
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }).addOnFailureListener(Throwable::printStackTrace);

    }
}