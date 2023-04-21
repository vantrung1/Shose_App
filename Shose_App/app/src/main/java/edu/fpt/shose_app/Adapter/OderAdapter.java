package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.shose_app.Activity.CancelOderBtnActivity;
import edu.fpt.shose_app.Activity.ProductDetailActivity;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;

public class OderAdapter extends RecyclerView.Adapter<OderAdapter.myviewHolder> {
    private Context context;
    private ArrayList<Oder> oderArrayList;

    public OderAdapter(Context context, ArrayList<Oder> oderArrayList) {
        this.context = context;
        this.oderArrayList = oderArrayList;
    }

    public void setorderlist(ArrayList<Oder> oderArrayList) {
        this.oderArrayList = oderArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OderAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_waitforconfirmation_oder, parent, false);
        return new OderAdapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OderAdapter.myviewHolder holder, int position) {
        Glide.with(context).load(oderArrayList.get(position).getProducts().get(0).getImage()).placeholder(R.drawable.product).into(holder.item_img_wait_for_confirm);
        holder.txtAttributes.setText(oderArrayList.get(position).getProducts().get(0).getAttributes() + "");
        holder.txtName.setText(oderArrayList.get(position).getProducts().get(0).getName() + "");
        holder.txtQuantity.setText("x" + oderArrayList.get(position).getProducts().get(0).getQuantity());
        holder.txt_quantity2.setText(oderArrayList.get(position).getQuantity() + "sản phẩm");
        holder.txtPrice.setText(new DecimalFormat("###,###,###").format(oderArrayList.get(position).getProducts().get(0).getPrice()));
        holder.txtPrice.setPaintFlags(  holder.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtSale.setText(new DecimalFormat("###,###,###").format(oderArrayList.get(position).getProducts().get(0).getSale()));
        holder.txtTotal.setText(oderArrayList.get(position).getTotal());
        holder.setListenner(new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1) {
                    Intent intent = new Intent(context, CancelOderBtnActivity.class);
                    intent.putExtra("oderConfirm", oderArrayList.get(pos));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return oderArrayList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_img_wait_for_confirm;
        TextView txtName, txtAttributes, txtQuantity, txt_quantity2, txtPrice, txtSale, txtTotal;
        AppCompatButton appCompatButton;
        ImageClickListenner listenner;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            item_img_wait_for_confirm = itemView.findViewById(R.id.item_img_wait_for_confirm);
            txtName = itemView.findViewById(R.id.item_txt_name_confirm);
            txtAttributes = itemView.findViewById(R.id.item_txt_attributes_confirm);
            txtQuantity = itemView.findViewById(R.id.item_txt_quantity_confirm);
            txt_quantity2 = itemView.findViewById(R.id.item_txt_quantity2);
            txtPrice = itemView.findViewById(R.id.item_txt_price_confirm);
            txtSale = itemView.findViewById(R.id.item_txt_sale_confirm);
            txtTotal = itemView.findViewById(R.id.item_txt_total_confirm);
            appCompatButton = itemView.findViewById(R.id.btn_wait_for_confirm);

            //eventClick
            itemView.setOnClickListener(this);
        }

        public void setListenner(ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                listenner.onImageClick(view, getAdapterPosition(), 1);
            }
        }
    }

    public interface ImageClickListenner {
        void onImageClick(View view, int pos, int giatri);
    }
}
