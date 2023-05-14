package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import edu.fpt.shose_app.Model.Brand;
import edu.fpt.shose_app.Model.starmodel;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.dialogModel.EventBus.BrandEvent;
import edu.fpt.shose_app.dialogModel.EventBus.starEvent;

public class starAtapter extends RecyclerView.Adapter<starAtapter.MyviewHolder> {
    Context context;
    List<starmodel> list;
    private int ischeckPostion = 0;
    public starAtapter(Context context, List<starmodel> list) {
        this.context = context;
        this.list = list;
    }
    public int getSelected(){
        if(ischeckPostion != -1){
            return list.get(ischeckPostion).getId();
        }
        else {
            return -1;
        }
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_star, parent, false);
        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
            holder.bind(list.get(position).getStar());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.starId);
        }
        private void bind(String s){
            text.setText(s);

            if(ischeckPostion == -1){

            }
            else {
                if (ischeckPostion == getAdapterPosition()){
                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5B9EE1")));
                }
                else {
                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F8F9FA")));
                }
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5B9EE1")));
                    if(ischeckPostion != getAdapterPosition()){
                        notifyItemChanged(ischeckPostion);
                        ischeckPostion = getAdapterPosition();
                        EventBus.getDefault().postSticky(new starEvent());
                    }
                }
            });
        }
    }
}
