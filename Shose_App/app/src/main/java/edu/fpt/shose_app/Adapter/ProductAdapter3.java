package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.fpt.shose_app.Activity.ProductDetailActivity;
import edu.fpt.shose_app.Interface.OnItemClickListener;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.Size;
import edu.fpt.shose_app.Model.SizeRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import edu.fpt.shose_app.dialogModel.dialogProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter3 extends RecyclerView.Adapter<ProductAdapter3.myviewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;
    private boolean mIsExpanded = false;
    Gson gson;
    Retrofit retrofit;
    ApiApp apiInterface;
    List<SizeRequest.SizeQuantity> sizeQuantityList;
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
                            dialogProduct dialog = new dialogProduct(context,productArrayList.get(i),new sizeAdapter(context, sizeList),sizeQuantityList);
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
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder  {
        ImageView itemproduct_img,itemClickFav;
        TextView itemproduct_name,itemproduct_price;
         OnItemClickListener onItemClickListener;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            itemproduct_img = itemView.findViewById(R.id.item_product_image);
            itemClickFav = itemView.findViewById(R.id.itemclcik);
            itemproduct_name = itemView.findViewById(R.id.item_product_name);
            itemproduct_price = itemView.findViewById(R.id.item_product_price);
        }

    }
}
