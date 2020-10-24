package com.lymin.nexonlinemarket.view.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.managers.WishListManager;
import com.lymin.nexonlinemarket.model.WishListItem;

import java.util.List;

public class WishLishAdapter extends RecyclerView.Adapter<WishLishAdapter.ViewHolder> {
    private List<WishListItem> listItems;
    private Context context;

    public WishLishAdapter(Context context) {
        this.listItems = new WishListManager().getList();
        this.context = context;
    }

    @NonNull
    @Override
    public WishLishAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wishlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishLishAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView price;
        public View lyt_parent;
        public ViewHolder(@NonNull View v) {
            super(v);

            image = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
            price = (TextView) v.findViewById(R.id.price);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }
}
