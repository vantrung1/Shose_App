package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import edu.fpt.shose_app.Model.Notification;
import edu.fpt.shose_app.R;

public class Notifi_adapter extends RecyclerView.Adapter<Notifi_adapter.myViewHolder> {
    private Context context;
    private List<Notification> NotifiList;

    public Notifi_adapter(Context context, List<Notification> notifiList) {
        this.context = context;
        NotifiList = notifiList;
    }

    public void setNotifiList(List<Notification> notifiList) {
        this.NotifiList = new ArrayList<>();
        this.NotifiList = notifiList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Notifi_adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notifi, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Notifi_adapter.myViewHolder holder, int position) {
        Notification notification = NotifiList.get(position);
        holder.title.setText(notification.getFcmRequest().getNotification().getTitle());
        holder.body.setText(notification.getFcmRequest().getNotification().getBody());
    }

    @Override
    public int getItemCount() {
        return NotifiList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleNotifi);
            body = itemView.findViewById(R.id.bodyNotifi);
        }
    }
}
