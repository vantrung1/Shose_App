package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import edu.fpt.shose_app.R;

public class ImageFeedbackAdapter extends RecyclerView.Adapter<ImageFeedbackAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Uri> uriArrayList;

    public ImageFeedbackAdapter(Context context, ArrayList<Uri> uriArrayList) {
        this.context = context;
        this.uriArrayList = uriArrayList;
    }

    @NonNull
    @Override
    public ImageFeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_feed_back, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageFeedbackAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(uriArrayList.get(position))
                .into(holder.img);

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uriArrayList.remove(uriArrayList.get(position));
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
            }
        });

    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img, imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image_view_item_feed_back);
            imgDelete = itemView.findViewById(R.id.image_delete_item);
        }
    }
}
