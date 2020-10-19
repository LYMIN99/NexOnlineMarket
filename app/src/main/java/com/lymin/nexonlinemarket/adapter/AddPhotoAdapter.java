package com.lymin.nexonlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lymin.nexonlinemarket.R;
import com.lymin.nexonlinemarket.model.PhotoUpload;

import java.util.List;

public class AddPhotoAdapter extends RecyclerView.Adapter<AddPhotoAdapter.ViewHolder> {
    private Context context;
    private List<PhotoUpload> list;

    public AddPhotoAdapter(Context context, List<PhotoUpload> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_upload, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.size()!=0)
        if (list.get(position).getUri()!=null){
            holder.imageView.setImageURI(list.get(position).getUri());
            holder.btnDelete.setVisibility(View.VISIBLE);
        }else {
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size()==0){
            return 5;
        }else {return list.size();}

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private ImageButton btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
