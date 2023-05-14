package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.R;

public class ImageFeedbackAdapter2 extends RecyclerView.Adapter<ImageFeedbackAdapter2.ViewHolder> {
    private Context context;
    private ArrayList<Image> uriArrayList;

    public ImageFeedbackAdapter2(Context context, ArrayList<Image> uriArrayList) {
        this.context = context;
        this.uriArrayList = uriArrayList;
    }

    @NonNull
    @Override
    public ImageFeedbackAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_feed_back, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageFeedbackAdapter2.ViewHolder holder, int position) {
        Glide.with(context).load(uriArrayList.get(position).getName())
                .into(holder.img);

        holder.imgDelete.setVisibility(View.GONE);

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
