package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.fpt.shose_app.Activity.FeedBackActivity;
import edu.fpt.shose_app.Activity.InforOderActivity;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.R;

public class DeliveredAdapter extends RecyclerView.Adapter<DeliveredAdapter.myviewHolder> {
    private edu.fpt.shose_app.Adapter.Products_Oder_Adapter products_oder_adapter;
    RecyclerView recyclerView;
    private Context context;
    private ArrayList<Oder> oderArrayList;

    public DeliveredAdapter(Context context, ArrayList<Oder> oderArrayList) {
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
    public DeliveredAdapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_delivered_oder, parent, false);
        return new DeliveredAdapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredAdapter.myviewHolder holder, int position) {
        holder.txt_quantity2.setText(oderArrayList.get(position).getQuantity() + "sản phẩm");
        holder.txtTotal.setText(oderArrayList.get(position).getTotal());
        holder.setListenner(new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1) {
                    Intent intent = new Intent(context, FeedBackActivity.class);
                    intent.putExtra("evaluate", oderArrayList.get(pos));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if (giatri == 2) {
                    Intent intent = new Intent(context, InforOderActivity.class);
                    intent.putExtra("inforoder", oderArrayList.get(pos));
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
        TextView txt_quantity2, txtTotal, txtShow;
        AppCompatButton appCompatButton1, appCompatButton2;
        ImageClickListenner listenner;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_quantity2 = itemView.findViewById(R.id.item_txt_quantity2);
            txtTotal = itemView.findViewById(R.id.item_txt_total_delivered);
            appCompatButton1 = itemView.findViewById(R.id.btn_delivered);
            appCompatButton2 = itemView.findViewById(R.id.btn_delivered_repurchase);
            txtShow = itemView.findViewById(R.id.txt_show_more);

            recyclerView = itemView.findViewById(R.id.recy_delivered);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            products_oder_adapter = new Products_Oder_Adapter(context, oderArrayList.get(0).getProducts());
            recyclerView.setAdapter(products_oder_adapter);

            if (oderArrayList.size()>1){
                txtShow.setVisibility(View.VISIBLE);
            }else {
                txtShow.setVisibility(View.GONE);
            }

            appCompatButton1.setOnClickListener(this);
            appCompatButton2.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void setListenner(DeliveredAdapter.ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == appCompatButton1) {
                listenner.onImageClick(view, getAdapterPosition(), 1);
            } else if (view == itemView) {
                listenner.onImageClick(view, getAdapterPosition(), 2);
            }
            else if (view == appCompatButton2) {
                listenner.onImageClick(view, getAdapterPosition(), 3);
            }
        }
    }

    public interface ImageClickListenner {
        void onImageClick(View view, int pos, int giatri);
    }
}
