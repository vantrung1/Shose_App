package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import edu.fpt.shose_app.Model.Products_Oder;
import edu.fpt.shose_app.R;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.MyViewHolder> {
    private Context context;
    private List<Products_Oder> products_oderList;

    public FeedBackAdapter(Context context, List<Products_Oder> products_oderList) {
        this.context = context;
        this.products_oderList = products_oderList;
    }

    @NonNull
    @Override
    public FeedBackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed_back, parent, false);
        return new FeedBackAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedBackAdapter.MyViewHolder holder, int position) {
        holder.bind(products_oderList.get(position));
    }

    @Override
    public int getItemCount() {
        return products_oderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtname, txtattributes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.item_image_evaluate);
            txtname = itemView.findViewById(R.id.txt_namepr_evaluate);
            txtattributes = itemView.findViewById(R.id.txt_attributes_evaluate);
        }
        void bind(final Products_Oder p_oder) {
            Glide.with(context).load(p_oder.getImage()).into(imgProduct);
            txtname.setText(p_oder.getName());
            txtattributes.setText(p_oder.getAttributes());
        }
    }
}
