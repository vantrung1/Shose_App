package edu.fpt.shose_app.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
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
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_price_cart.setText("đ" + decimalFormat.format((cart.getPrice())));
        holder.txt_price_cart.setPaintFlags(holder.txt_price_cart.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txt_sale_cart.setText("đ" + decimalFormat.format((cart.getPrice())));
        holder.txt_quantity_cart.setText(cart.getQuantity() + "");
        holder.txt_color_cart.setText("Phan loai: " + cart.getColor() + ", ");
        holder.txt_size_cart.setText(cart.getSize() + "");
        holder.checkbox_item_cart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Utils.buyCartLits.add(cart);
                    EventBus.getDefault().postSticky(new TotalEvent());
                } else {
                    for (int i = 0; i < Utils.buyCartLits.size(); i++) {
                        if (Utils.buyCartLits.get(i).getIdCart() == cart.getIdCart()) {
                            Utils.buyCartLits.remove(i);
                            EventBus.getDefault().postSticky(new TotalEvent());
                        }
                    }
                }
            }
        });
        Glide.with(context).load(cart.getImage()).into(holder.img_cart);

        holder.setListenner(new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1) {
                    if (cartList.get(pos).getQuantity() > 1) {
                        int quantityNew = cartList.get(pos).getQuantity() - 1;
                        cartList.get(pos).setQuantity(quantityNew);
                        EventBus.getDefault().postSticky(new TotalEvent());
                        holder.txt_quantity_cart.setText(cartList.get(pos).getQuantity() + "");
                    } else if (cartList.get(pos).getQuantity() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thong bao");
                        builder.setMessage("ban co muon xoa san pham nay khoi gio hang?");
                        builder.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cartList.remove(pos);
                                notifyDataSetChanged();
                                dialogInterface.dismiss();
                                EventBus.getDefault().postSticky(new TotalEvent());
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
                    if (cartList.get(pos).getQuantity() >= 1) {
                        int quantityNew = cartList.get(pos).getQuantity() + 1;
                        cartList.get(pos).setQuantity(quantityNew);
                        holder.txt_quantity_cart.setText(cartList.get(pos).getQuantity() + "");
                        EventBus.getDefault().postSticky(new TotalEvent());
                    }
                }
                if (giatri == 3) {
                    final Dialog dialog = new Dialog(view.getRootView().getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_update_cart);

                    ImageView img_update_cart = dialog.findViewById(R.id.img_update_cart);
                    ImageView img_close = dialog.findViewById(R.id.img_close);
                    ImageView item_btnIMG_minus_cart = dialog.findViewById(R.id.item_btnIMG_minus_cart);
                    ImageView item_btnIMG_plus_cart = dialog.findViewById(R.id.item_btnIMG_plus_cart);
                    TextView txt_price = dialog.findViewById(R.id.txt_price);
                    TextView txt_sale = dialog.findViewById(R.id.txt_sale);
                    TextView txt_quantity = dialog.findViewById(R.id.txt_quantity);
                    TextView txt_quantity_cart_update = dialog.findViewById(R.id.item_txt_quantity_cart_update);

                    RecyclerView recycler_dialog_color = dialog.findViewById(R.id.recycler_dialog_color);
                    RecyclerView recycler_dialog_size = dialog.findViewById(R.id.recycler_dialog_size);
                    AppCompatButton appCompatButton2 = dialog.findViewById(R.id.btn_xacnhan);

                    Glide.with(context).load(cart.getImage()).into(img_update_cart);
                    txt_price.setText(holder.txt_price_cart.getText());
                    txt_price.setPaintFlags(txt_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    txt_sale.setText(holder.txt_price_cart.getText());
                    txt_quantity_cart_update.setText(holder.txt_quantity_cart.getText());

                    img_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_cart, img_plus_cart, img_minus_cart;
        TextView txt_name_cart, txt_price_cart, txt_quantity_cart, txt_color_cart, txt_size_cart, txt_sale_cart;
        ImageClickListenner listenner;
        CheckBox checkbox_item_cart;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cart = itemView.findViewById(R.id.item_img_cart);
            img_plus_cart = itemView.findViewById(R.id.item_btnIMG_plus_cart);
            img_minus_cart = itemView.findViewById(R.id.item_btnIMG_minus_cart);
            txt_name_cart = itemView.findViewById(R.id.item_txt_name_cart);
            txt_price_cart = itemView.findViewById(R.id.item_txt_price_cart);
            txt_sale_cart = itemView.findViewById(R.id.item_txt_sale_cart);
            txt_quantity_cart = itemView.findViewById(R.id.item_txt_quantity_cart);
            txt_color_cart = itemView.findViewById(R.id.item_txt_color_cart);
            txt_size_cart = itemView.findViewById(R.id.item_txt_size_cart);
            checkbox_item_cart = itemView.findViewById(R.id.checkbox_item_cart);

            //eventClick
            img_minus_cart.setOnClickListener(this);
            img_plus_cart.setOnClickListener(this);
            itemView.setOnClickListener(this);
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
            } else if (view == itemView) {
                listenner.onImageClick(view, getAdapterPosition(), 3);
            }
        }
    }

    public interface ImageClickListenner {
        void onImageClick(View view, int pos, int giatri);
    }

}
