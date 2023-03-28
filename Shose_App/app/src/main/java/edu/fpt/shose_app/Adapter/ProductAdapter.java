package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.fpt.shose_app.Activity.HomeActivity;
import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.abc;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Utils.Utils;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.myviewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;

    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }
    public void setBrandSelected(ArrayList<Product> productArrayList){
        this.productArrayList = new ArrayList<>();
        this.productArrayList = productArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home,parent,false);
        return  new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.myviewHolder holder, int position) {
        Gson gson = new Gson();
        String jonsimage = productArrayList.get(position).getImage();

        Type listType = new TypeToken<List<Image>>(){}.getType();
        List<Image> myObjects = gson.fromJson(jonsimage, listType);
        Glide.with(context).load(myObjects.get(0).getImage()).into(holder.itemproduct_img);
       // Log.d("TAG", "onBindViewHolder: "+myObjects.get(0).getImage());
        holder.itemproduct_name.setText(productArrayList.get(position).getName());
        holder.itemproduct_price.setText(productArrayList.get(position).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder {
        ImageView itemproduct_img;
        TextView itemproduct_name,itemproduct_price;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            itemproduct_img = itemView.findViewById(R.id.item_product_image);
            itemproduct_name = itemView.findViewById(R.id.item_product_name);
            itemproduct_price = itemView.findViewById(R.id.item_product_price);
        }
    }
}
