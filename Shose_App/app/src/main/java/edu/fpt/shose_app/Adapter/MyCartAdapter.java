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

import java.util.List;

import edu.fpt.shose_app.Model.Cart;
import edu.fpt.shose_app.R;


public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.myViewHolder> {
    private Context context;
    private List<Cart> cartList;

    public MyCartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_cart, parent, false);
        return new MyCartAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        myViewHolder myViewHolder = holder;
        Cart cart = cartList.get(position);
        myViewHolder.txt_name_cart.setText(cart.getCartName());
        myViewHolder.txt_price_cart.setText(cart.getPrice()+"");
        myViewHolder.txt_quantity_cart.setText(cart.getQuantity()+"");
        myViewHolder.txt_size_cart.setText(cart.getSize()+"");
        Glide.with(context).load(cart.getImage()).into(holder.img_cart);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img_cart, img_plus_cart, img_minus_cart, delete_cart;
        TextView txt_name_cart, txt_price_cart, txt_quantity_cart, txt_size_cart;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cart = itemView.findViewById(R.id.item_img_cart);
            img_plus_cart = itemView.findViewById(R.id.item_btnIMG_plus_cart);
            img_minus_cart = itemView.findViewById(R.id.item_btnIMG_minus_cart);
            delete_cart = itemView.findViewById(R.id.item_img_delete_cart);

            txt_name_cart = itemView.findViewById(R.id.item_txt_name_cart);
            txt_price_cart = itemView.findViewById(R.id.item_txt_price_cart);
            txt_quantity_cart = itemView.findViewById(R.id.item_txt_quantity_cart);
            txt_size_cart = itemView.findViewById(R.id.item_txt_size_cart);
        }

    }
}
