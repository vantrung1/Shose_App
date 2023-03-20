package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;

public class ProducAdapter2 extends RecyclerView.Adapter<ProducAdapter2.myViewHolder> {
    private Context context;
    private ArrayList<Product> productArrayList;

    public ProducAdapter2(Context context, ArrayList<Product> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProducAdapter2.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product2,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducAdapter2.myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
