package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;

public class ProducAdapter2 extends RecyclerView.Adapter<ProducAdapter2.myViewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;

    public ProducAdapter2(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }
    public void setProdcut(ArrayList<Product> productArrayList){
        this.productArrayList = new ArrayList<>();
        this.productArrayList = productArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProducAdapter2.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product2,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducAdapter2.myViewHolder holder, int position) {

        Glide.with(context).load(productArrayList.get(position).getImage().get(0).get("1").getName()).into(holder.itemproduct_img2);
        // Log.d("TAG", "onBindViewHolder: "+myObjects.get(0).getImage());
        holder.itemproduct_name2.setText(productArrayList.get(position).getName());
        holder.itemproduct_price2.setText(productArrayList.get(position).getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView itemproduct_img2;
        TextView itemproduct_name2,itemproduct_price2;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            itemproduct_img2 = itemView.findViewById(R.id.item_product_img2);
            itemproduct_name2 = itemView.findViewById(R.id.item_product_name2);
            itemproduct_price2 = itemView.findViewById(R.id.item_product_price2);
        }
    }
}
