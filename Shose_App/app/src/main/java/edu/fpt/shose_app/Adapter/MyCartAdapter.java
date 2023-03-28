package edu.fpt.shose_app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import edu.fpt.shose_app.EventBus.TotalEvent;
import edu.fpt.shose_app.Model.Cart;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Utils.Utils;


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
        Cart cart = cartList.get(position);
        holder.txt_name_cart.setText(cart.getCartName());
        holder.txt_price_cart.setText(cart.getPrice() + "");
        holder.txt_quantity_cart.setText(cart.getQuantity() + "");
        holder.txt_size_cart.setText(cart.getSize() + "");
        Glide.with(context).load(cart.getImage()).into(holder.img_cart);
//        int priceNew = cart.getQuantity() * cart.getPrice();
//        holder.txt_price_cart.setText(priceNew);
        holder.setListenner(new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1) {
                    if (cartList.get(pos).getQuantity() > 1) {
                        int quantityNew = cartList.get(pos).getQuantity() - 1;
                        cartList.get(pos).setQuantity(quantityNew);
                    } else if (cartList.get(pos).getQuantity() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thong bao");
                        builder.setMessage("ban co muon xoa san pham nay khoi gio hang?");
                        builder.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cartList.remove(pos);
                                notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("Huy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                }
                if (giatri == 2) {
                    if (cartList.get(pos).getQuantity() >= 0) {
                        int quantityNew = cartList.get(pos).getQuantity() + 1;
                        cartList.get(pos).setQuantity(quantityNew);
                    }
                }
                holder.txt_quantity_cart.setText(cartList.get(pos).getQuantity() + "");
                EventBus.getDefault().postSticky(new TotalEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_cart, img_plus_cart, img_minus_cart, delete_cart;
        TextView txt_name_cart, txt_price_cart, txt_quantity_cart, txt_size_cart;
        ImageClickListenner listenner;

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

            img_minus_cart.setOnClickListener(this);
            img_plus_cart.setOnClickListener(this);
        }

        public void setListenner(ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == img_minus_cart) {
                listenner.onImageClick(view, getAdapterPosition(), 1);
            } else if (view == img_plus_cart) {
                listenner.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }

    public interface ImageClickListenner {
        void onImageClick(View view, int pos, int giatri);
    }
}
