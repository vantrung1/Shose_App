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
import edu.fpt.shose_app.Activity.InforOderActivity;
import edu.fpt.shose_app.Interface.ImageClickr;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.R;

public class TransportAtapter extends RecyclerView.Adapter<TransportAtapter.myviewHolder> {
    private edu.fpt.shose_app.Adapter.Products_Oder_Adapter products_oder_adapter;
    RecyclerView recyclerView;
    private Context context;
    private ArrayList<Oder> oderArrayList;
    private ImageClickr imageClickr;
    public TransportAtapter(Context context, ArrayList<Oder> oderArrayList, ImageClickr imageClickr) {
        this.context = context;
        this.oderArrayList = oderArrayList;
        this.imageClickr= imageClickr;
    }

    public void setorderlist(ArrayList<Oder> oderArrayList) {
        this.oderArrayList = new ArrayList<>();
        this.oderArrayList = oderArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransportAtapter.myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transport_oder, parent, false);
        return new TransportAtapter.myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransportAtapter.myviewHolder holder, int position) {
        holder.txt_quantity2.setText(oderArrayList.get(position).getQuantity() + "sản phẩm");
        holder.txtTotal.setText(oderArrayList.get(position).getTotal());

        holder.setListenner(new TransportAtapter.ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1) {
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
        AppCompatButton appCompatButton;
        ImageClickListenner listenner;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            txt_quantity2 = itemView.findViewById(R.id.item_txt_quantity2);
            txtTotal = itemView.findViewById(R.id.item_txt_total_transport);
            appCompatButton = itemView.findViewById(R.id.btn_transport);
            txtShow = itemView.findViewById(R.id.txt_show_more);
            recyclerView = itemView.findViewById(R.id.recy_transport);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            products_oder_adapter = new Products_Oder_Adapter(context, oderArrayList.get(0).getProducts(),imageClickr);
            recyclerView.setAdapter(products_oder_adapter);

            if (oderArrayList.size() > 1) {
                txtShow.setVisibility(View.VISIBLE);
            } else {
                txtShow.setVisibility(View.GONE);
            }
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
