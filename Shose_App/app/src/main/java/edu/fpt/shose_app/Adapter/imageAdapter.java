package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import edu.fpt.shose_app.dialogModel.EventBus.ImageEvent;
import edu.fpt.shose_app.R;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.ViewHolder> {
    private int ischeckPostion = 0;
    private List<String> imageUrls;
    private Context context;

    public imageAdapter(Context context, List<String> imageUrls) {
        this.imageUrls = imageUrls;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(imageUrls.get(position));
    }
    public String getSelected(){
        if(ischeckPostion != -1){
            return imageUrls.get(ischeckPostion);
        }
        else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_item);
        }
        void bind( final String image){
            Glide.with(context)
                    .load(image)
                    .into(imageView);
            if(ischeckPostion == -1){

            }
            else {
                if (ischeckPostion == getAdapterPosition()){

                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5B9EE1")));
                }
                else {

                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F8F9FA")));
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5B9EE1")));
                    if(ischeckPostion != getAdapterPosition()){
                        notifyItemChanged(ischeckPostion);
                        ischeckPostion = getAdapterPosition();
                        EventBus.getDefault().postSticky(new ImageEvent());
                    }
                }
            });
        }
    }

}
