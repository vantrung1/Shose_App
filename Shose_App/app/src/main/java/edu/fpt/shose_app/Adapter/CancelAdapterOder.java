package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.fpt.shose_app.Activity.CancelOderBtnActivity;
import edu.fpt.shose_app.Activity.Cancel_detail_activity;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.R;

public class CancelAdapterOder extends RecyclerView.Adapter<CancelAdapterOder.myviewHolder> {
    private edu.fpt.shose_app.Adapter.Products_Oder_Adapter products_oder_adapter;
    RecyclerView recyclerView;
    private Context context;
    private ArrayList<Oder> oderArrayList;

    public CancelAdapterOder(Context context, ArrayList<Oder> oderArrayList) {
        this.context = context;
        this.oderArrayList = oderArrayList;
    }

    public void setorderlist(ArrayList<Oder> oderArrayList) {
        this.oderArrayList = new ArrayList<>();
        this.oderArrayList = oderArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CancelAdapterOder.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cancel_oder, parent, false);
        return new CancelAdapterOder.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancelAdapterOder.myviewHolder holder, int position) {
        holder.txt_quantity2.setText(oderArrayList.get(position).getQuantity() + "sản phẩm");
        holder.txtTotal.setText(oderArrayList.get(position).getTotal());

        holder.setListenner(new CancelAdapterOder.ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1 | giatri == 2) {
                    Intent intent = new Intent(context, Cancel_detail_activity.class);
                    intent.putExtra("oder_cancel_detail", oderArrayList.get(pos));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if (giatri == 3) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    products_oder_adapter = new Products_Oder_Adapter(context, oderArrayList.get(0).getProducts());
                    recyclerView.setAdapter(products_oder_adapter);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return oderArrayList.size();
    }

    public class myviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_quantity2, txtTotal, txtShow;
        AppCompatButton appCompatButton;
        ImageClickListenner listenner;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_quantity2 = itemView.findViewById(R.id.item_txt_quantity2);
            txtTotal = itemView.findViewById(R.id.item_txt_total_cancel);
            appCompatButton = itemView.findViewById(R.id.btn_detail_cancel);
            txtShow = itemView.findViewById(R.id.txt_show_more);

            recyclerView = itemView.findViewById(R.id.recy_cancel_oder);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            products_oder_adapter = new Products_Oder_Adapter(context, oderArrayList.get(0).getProducts());
            recyclerView.setAdapter(products_oder_adapter);
            appCompatButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
            txtShow.setOnClickListener(this);
        }

        public void setListenner(CancelAdapterOder.ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == itemView) {
                listenner.onImageClick(view, getAdapterPosition(), 1);
            } else if (view == appCompatButton) {
                listenner.onImageClick(view, getAdapterPosition(), 2);
            } else if (view == txtShow) {
                listenner.onImageClick(view, getAdapterPosition(), 3);
            }
        }
    }

    public interface ImageClickListenner {
        void onImageClick(View view, int pos, int giatri);
    }
}
