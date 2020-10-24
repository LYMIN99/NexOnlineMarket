package com.lymin.nexonlinemarket.view.activity.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.adapter.AdapterShopProductCard;
import com.lymin.nexonlinemarket.databinding.ActivitySaleDetailBinding;
import com.lymin.nexonlinemarket.managers.WishListManager;
import com.lymin.nexonlinemarket.model.PhotosSale;
import com.lymin.nexonlinemarket.model.SaleItem;
import com.lymin.nexonlinemarket.model.WishListItem;
import com.lymin.nexonlinemarket.utils.Tools;
import com.lymin.nexonlinemarket.view.activity.category.ShoppingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class SaleDetailActivity extends AppCompatActivity {

    private AdapterImageSlider adapterImageSlider;

    private ActivitySaleDetailBinding binding;
    private String id;
    private SaleItem saleItemss = new SaleItem();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sale_detail);

        id = getIntent().getStringExtra("id");
        getInfo(id);

        initClick();
    }

    private void initClick() {
        binding.btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishListItem item = new WishListItem();
                item.setId(id);
                item.setPrice(saleItemss.getPrice());
                item.setDateTime(saleItemss.getDateTime());
                item.setProductName(saleItemss.getProductName());
                item.setThumbnail(saleItemss.getThumbnail());

                new WishListManager().addItem(item);
            }
        });
    }

    private void getInfo(String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Products").whereEqualTo("id",id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            SaleItem saleItem = document.toObject(SaleItem.class);
                            binding.nameView.setText(saleItem.getProductName());
                            binding.tvPrice.setText("$ "+saleItem.getPrice());
                            binding.tvLocation.setText(saleItem.getLocation());
                            binding.tvDatetime.setText(saleItem.getDateTime());
                            binding.tvCategory.setText(saleItem.getCategoryID());
                            binding.tvType.setText(saleItem.getTypeID());
                            binding.tvCondition.setText(saleItem.getConditions());
                            binding.tvDec.setText(saleItem.getDescription());
                            binding.tvUsername.setText(saleItem.getSellerName());
//                            binding.imageProfile.setImageURI(saleItem.get);
                            binding.tvPhonenumber.setText(saleItem.getPhoneNumbers());
                          //  binding.tvEmail.setText(saleItem.get);

                            saleItemss = saleItem;
                            document.getReference().collection("Image").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()){
                                        ArrayList<String>list = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                            list.add(document.getString("Image"));
                                        }
                                        initComponent(list);
                                    }
                                }
                            });
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initComponent(ArrayList<String> images) {
        adapterImageSlider = new AdapterImageSlider(SaleDetailActivity.this, new ArrayList<>());
        adapterImageSlider.setItems(images);
        binding.pager.setAdapter(adapterImageSlider);

    // displaying selected image first
        binding.pager.setCurrentItem(0);
        binding.tvCount.setText("1 of "+adapterImageSlider.getCount());
    addBottomDots(binding.layoutDots, adapterImageSlider.getCount(), 0);

        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onPageSelected(int pos) {
            addBottomDots(binding.layoutDots, adapterImageSlider.getCount(), pos);
            binding.tvCount.setText((pos+1)+" of "+adapterImageSlider.getCount());
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    });

}

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(SaleDetailActivity.this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            dots[i].setColorFilter(ContextCompat.getColor(SaleDetailActivity.this, R.color.grey_40), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
            dots[current].setColorFilter(ContextCompat.getColor(SaleDetailActivity.this, R.color.grey_40), PorterDuff.Mode.SRC_ATOP);
        }
    }

private static class AdapterImageSlider extends PagerAdapter {

    private Activity act;
    private ArrayList<String> items;

    private AdapterImageSlider.OnItemClickListener onItemClickListener;

    private interface OnItemClickListener {
        void onItemClick(View view, Image obj);
    }

    public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // constructor
    private AdapterImageSlider(Activity activity, ArrayList<String> items) {
        this.act = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    public String getItem(int pos) {
        return items.get(pos);
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final String o = items.get(position);
        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_slider_image, container, false);

        ImageView image = v.findViewById(R.id.image);
        Glide.with(act).load(o).into(image);
        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);

    }

}
}