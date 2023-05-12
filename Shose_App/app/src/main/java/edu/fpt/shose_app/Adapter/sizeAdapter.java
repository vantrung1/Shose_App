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

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import edu.fpt.shose_app.dialogModel.EventBus.SizeEvent;
import edu.fpt.shose_app.Model.Size;
import edu.fpt.shose_app.R;

public class sizeAdapter extends RecyclerView.Adapter<sizeAdapter.MyViewHolder> {
    private Context context;
    private List<Size > sizeList;
    private int ischeckPostion = 0;
    public sizeAdapter(Context context, List<edu.fpt.shose_app.Model.Size> sizeList) {
        this.context = context;
        this.sizeList = sizeList;
    }

    @NonNull
    @Override
    public sizeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemsize, parent, false);
        return new sizeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sizeAdapter.MyViewHolder holder, int position) {
            holder.bind(sizeList.get(position));
    }
    public String getSelected(){
        if(ischeckPostion != -1){
            return sizeList.get(ischeckPostion).getSize();
        }
        else {
            return null;
        }
    }
    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.size_name);
        }
        void bind(edu.fpt.shose_app.Model.Size size){
            textView.setText(size.getSize());
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
                        EventBus.getDefault().postSticky(new SizeEvent());
                    }
                }
            });
        }
    }
}
