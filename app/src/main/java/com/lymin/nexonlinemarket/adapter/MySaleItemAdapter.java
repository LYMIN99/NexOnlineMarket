package com.lymin.nexonlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.model.SaleItem;

import java.util.ArrayList;
import java.util.List;

public class MySaleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SaleItem> items = new ArrayList<>();
    private Context ctx;

    public MySaleItemAdapter(Context context, List<SaleItem> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public ImageButton btnDelete;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
            price = (TextView) v.findViewById(R.id.price);
            btnDelete = (ImageButton) v.findViewById(R.id.btn_delete);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale_linear, parent, false);
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

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}