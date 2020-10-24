package com.lymin.nexonlinemarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.model.PhotoUpload;
import com.lymin.nexonlinemarket.model.SaleItem;
import com.lymin.nexonlinemarket.utils.Tools;
import com.lymin.nexonlinemarket.view.activity.shop.SaleDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class AdapterShopProductCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SaleItem> items = new ArrayList<>();
    private Context ctx;

    public AdapterShopProductCard(Context context, List<SaleItem> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
            price = (TextView) v.findViewById(R.id.price);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_product_card, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;

            final SaleItem p = items.get(position);
            view.title.setText(p.getProductName());
            view.price.setText("USD $"+p.getPrice());
            Glide.with(ctx).load(p.getThumbnail()).into(view.image);
            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ctx.startActivity(new Intent(ctx, SaleDetailActivity.class).putExtra("id",p.getId()));
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}