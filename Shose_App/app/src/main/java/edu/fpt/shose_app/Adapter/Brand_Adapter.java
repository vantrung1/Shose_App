package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Brand_Adapter extends RecyclerView.Adapter<Brand_Adapter.myViewHolder> {
    private Context context;

    private ArrayList<Brand> brandList;
    private int ischeckPostion = 0;

    public Brand_Adapter(Context context, ArrayList<Brand> brandList) {
        this.context = context;
        this.brandList = brandList;

    }
    public void setBrandSelected(ArrayList<Brand> brandList){
        this.brandList = new ArrayList<>();
        this.brandList = brandList;
        notifyDataSetChanged();


    }
    @NonNull
    @Override
    public Brand_Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand,parent,false);
        return  new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Brand_Adapter.myViewHolder holder, int position) {
        holder.bind(brandList.get(position));

    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }
    public Brand getSelected(){
        if(ischeckPostion != -1){
            return brandList.get(ischeckPostion);
        }
        else {
            return null;
        }
    }
    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView name_brand;
        ImageView imageView_brand;
        private CardView ln_item_dv;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name_brand = itemView.findViewById(R.id.name_brand);
            imageView_brand = itemView.findViewById(R.id.image_brand);
        }
        void bind( final Brand brand){
            name_brand.setText(brand.getBrandName());
            Glide.with(context).load(brand.getImage()).into(imageView_brand);
            if(ischeckPostion == -1){

            }
            else {
                if (ischeckPostion == getAdapterPosition()){
                    name_brand.setVisibility(View.VISIBLE);
                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5B9EE1")));
                }
                else {
                    name_brand.setVisibility(View.GONE);
                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F8F9FA")));
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name_brand.setVisibility(View.VISIBLE);
                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5B9EE1")));
                    if(ischeckPostion != getAdapterPosition()){
                        notifyItemChanged(ischeckPostion);
                        ischeckPostion = getAdapterPosition();
                    }
                }
            });
        }

    }
}
