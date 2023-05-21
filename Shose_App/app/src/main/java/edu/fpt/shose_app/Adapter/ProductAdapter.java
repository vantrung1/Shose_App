package edu.fpt.shose_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.fpt.shose_app.Activity.HomeActivity;
import edu.fpt.shose_app.Activity.ProductDetailActivity;
import edu.fpt.shose_app.Interface.OnItemClickListener;
import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.Products_Oder;
import edu.fpt.shose_app.Model.Size;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.Model.abc;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import edu.fpt.shose_app.dialogModel.dialogProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.myviewHolder> implements Filterable {
    private Context context;
    private ArrayList<Product> productListFull;
    private ArrayList<Product> productArrayList;


    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    List<SizeRequest.SizeQuantity> sizeQuantityList;
    public ProductAdapter(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
        productListFull = new ArrayList<>(productArrayList);
    }
    public void setBrandSelected(ArrayList<Product> productArrayList){
        this.productArrayList = productArrayList;
        this.productListFull = new ArrayList<>(productArrayList); // sao chép danh sách sản phẩm vào danh sách đầy đủ

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProductAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_home,parent,false);
        return  new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.myviewHolder holder, @SuppressLint("RecyclerView") int i) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### VNĐ");


        holder.txtDaban.setText("Đã bán: "+getSoLuong(productArrayList.get(i).getId()));
        Glide.with(context).load(productArrayList.get(i).getImage().get(0).get("image1").getName()).into(holder.itemproduct_img);
        // Log.d("TAG", "onBindViewHolder: "+myObjects.get(0).getImage());
        holder.itemproduct_name.setText(productArrayList.get(i).getName());
        if(productArrayList.get(i).getQuantity()==null||productArrayList.get(i).getQuantity().equals("0")){
            holder.status.setVisibility(View.VISIBLE);
        }
        else {
            holder.status.setVisibility(View.INVISIBLE);
        }
        holder.itemproduct_price.setText(decimalFormat.format(productArrayList.get(i).getPrice()));
        holder.itemClickFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeQuantityList  = new ArrayList<>();
                gson =new Gson();
                retrofit = new Retrofit.Builder()
                        .baseUrl(Utils.BASE_URL_API)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                List<Size> sizeList = new ArrayList<>();
                for (Map<String, Size> sizeMap : productArrayList.get(i).getSize()) {
                    for (Map.Entry<String, Size> entry : sizeMap.entrySet()) {
                        Size size = entry.getValue();
                        sizeList.add(size);
                    }
                }

                apiInterface = retrofit.create(ApiApp.class);
                Call<SizeRequest> objgetBrands = apiInterface.getQuantitySize(productArrayList.get(i).getId());
                // thực hiện gọi
                objgetBrands.enqueue(new Callback<SizeRequest>() {
                    @Override
                    public void onResponse(Call<SizeRequest> call, Response<SizeRequest> response) {
                        if(response.isSuccessful()){
                            SizeRequest sizeRequest = response.body();
                            sizeQuantityList = sizeRequest.getData();
                            dialogProduct dialog = new dialogProduct(context,productArrayList.get(i),new sizeAdapter(context, sizeQuantityList),sizeQuantityList);
                            dialog.show();
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            dialog.getWindow().setGravity(Gravity.BOTTOM);
                        }
                    }

                    @Override
                    public void onFailure(Call<SizeRequest> call, Throwable t) {
                        Log.d("ssssssssss", "onFailure: "+t.getLocalizedMessage());
                    }
                });

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product",productArrayList.get(i));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    private int getSoLuong(int id) {
        List<Products_Oder> products_oders = new ArrayList<>();
        int soluongdaban = 0 ;
        for(Oder oder :Utils.oderArrayList){
            for(Products_Oder oder1: oder.getProducts()){
                products_oders.add(oder1);
            }
        }
        for(Products_Oder oder: products_oders){
            if(oder.getProduct_id() == id){
                soluongdaban = soluongdaban+ oder.getQuantity();
            }
        }
        return soluongdaban;
    }
    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
    @Override
    public Filter getFilter() {
        return productFilter;
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(productListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Product product : productListFull) {
                    if (product.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(product);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productArrayList.clear();
            productArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class myviewHolder extends RecyclerView.ViewHolder  {
        ImageView itemproduct_img,itemClickFav;
        TextView itemproduct_name,itemproduct_price,status,txtDaban;
        OnItemClickListener onItemClickListener;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            itemproduct_img = itemView.findViewById(R.id.item_product_image);
            itemClickFav = itemView.findViewById(R.id.itemclcik);
            status = itemView.findViewById(R.id.status);
            itemproduct_name = itemView.findViewById(R.id.item_product_name);
            itemproduct_price = itemView.findViewById(R.id.item_product_price);
            txtDaban = itemView.findViewById(R.id.txtDaban);
        }

    }
    private void getQuantilySize(int id) {

    }
}
