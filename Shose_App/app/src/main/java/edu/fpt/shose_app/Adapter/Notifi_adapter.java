package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import edu.fpt.shose_app.Activity.ChatBoxActivity;
import edu.fpt.shose_app.Activity.OderActivity;
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
        Date currentDate = new Date();

        String pattern ="EEE MMM dd HH:mm:ss zzz yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = dateFormat.parse(notification.getTimestap());
            long timeDiff = currentDate.getTime() - date.getTime();
            long minutes = timeDiff / (60 * 1000); // Đơn vị phút
            long hours = timeDiff / (60 * 60 * 1000); // Đơn vị giờ
            long days = timeDiff / (24 * 60 * 60 * 1000); // Đơn vị ngày

            if(days>=1){
                holder.time.setText(days+" ngày trước");
            }
            else if(hours>=1){
                holder.time.setText(hours+" giờ trước");
            }
            else {
                holder.time.setText(minutes+" phút trước");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.time.setText(notification.getTimestap());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.title.getText().toString().equals("Tin Nhắn")){
                    Intent intent = new Intent(context, ChatBoxActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else if (holder.title.getText().toString().equals("Thông báo đơn hàng")) {
                    Intent intent = new Intent(context, OderActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return NotifiList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView title, body,time;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleNotifi);
            body = itemView.findViewById(R.id.bodyNotifi);
            time = itemView.findViewById(R.id.txttime);

        }
        public void actinon(Notification notificationmodel){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
