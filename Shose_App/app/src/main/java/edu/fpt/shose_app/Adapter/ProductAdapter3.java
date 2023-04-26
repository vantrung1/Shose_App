package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.shose_app.Activity.ProductDetailActivity;
import edu.fpt.shose_app.Interface.OnItemClickListener;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;

public class ProductAdapter3 extends RecyclerView.Adapter<ProductAdapter3.myviewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;
    private boolean mIsExpanded = false;

    public ProductAdapter3(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }
    public void setProductArrayList(ArrayList<Product> productArrayList){
        this.productArrayList = new ArrayList<>();
        this.productArrayList = productArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductAdapter3.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home,parent,false);
        return  new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter3.myviewHolder holder, int i) {

        Glide.with(context).load(productArrayList.get(i).getImage().get(0).get("image1").getName()).into(holder.itemproduct_img);
       // Log.d("TAG", "onBindViewHolder: "+myObjects.get(0).getImage());
        holder.itemproduct_name.setText(productArrayList.get(i).getName());
        holder.itemproduct_price.setText(new DecimalFormat("###,###,### VNĐ").format(productArrayList.get(i).getPrice()));

        holder.itemproduct_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product",productArrayList.get(i));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder  {
        ImageView itemproduct_img;
        TextView itemproduct_name,itemproduct_price;
         OnItemClickListener onItemClickListener;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            itemproduct_img = itemView.findViewById(R.id.item_product_image);
            itemproduct_name = itemView.findViewById(R.id.item_product_name);
            itemproduct_price = itemView.findViewById(R.id.item_product_price);
        }

    }
}
