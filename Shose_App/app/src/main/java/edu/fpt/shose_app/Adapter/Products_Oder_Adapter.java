package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import edu.fpt.shose_app.Model.Products_Oder;
import edu.fpt.shose_app.R;

public class Products_Oder_Adapter extends RecyclerView.Adapter<Products_Oder_Adapter.MyViewHolder> {
    private Context context;
    private List<Products_Oder> products_oderList;

    public Products_Oder_Adapter(Context context, List<Products_Oder> products_oderList) {
        this.context = context;
        this.products_oderList = products_oderList;
    }

    @NonNull
    @Override
    public Products_Oder_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_products_oder, parent, false);
        return new Products_Oder_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Products_Oder_Adapter.MyViewHolder holder, int position) {
        holder.bind(products_oderList.get(position));
    }

    @Override
    public int getItemCount() {
        return products_oderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtname, txtattributes, txtquantity, txtprice, txtsale;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.item_image);
            txtname = itemView.findViewById(R.id.item_txt_name);
            txtattributes = itemView.findViewById(R.id.item_txt_attributes);
            txtquantity = itemView.findViewById(R.id.item_txt_quantity);
            txtprice = itemView.findViewById(R.id.item_txt_price);
            txtsale = itemView.findViewById(R.id.item_txt_sale);
        }

        void bind(final Products_Oder p_oder) {
            Glide.with(context).load(p_oder.getImage()).into(imgProduct);
            txtname.setText(p_oder.getName());
            txtattributes.setText(p_oder.getAttributes());
            txtquantity.setText("x" + p_oder.getQuantity() + "");
            txtprice.setText(new DecimalFormat("###,###,###").format(p_oder.getPrice()));
            txtprice.setPaintFlags(txtprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtsale.setText(new DecimalFormat("###,###,###").format(p_oder.getSale()));
        }
    }
}
