package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.fpt.shose_app.Model.ChatMessage;
import edu.fpt.shose_app.R;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
private Context context;
private List<ChatMessage> chatMessageList;
private String sendid;
private static final  int TYPE_SEND = 1;
private static final  int TYPE_RECIEVED = 2;

    public ChatAdapter(Context context, List<ChatMessage> chatMessageList, String sendid) {
        this.context = context;
        this.chatMessageList = chatMessageList;
        this.sendid = sendid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        if (viewType == TYPE_SEND){
            view = LayoutInflater.from(context).inflate(R.layout.item_sendmess,parent,false);
            return new SendMessViewHolder(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_receied,parent,false);
            return new ReceivedMessViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==TYPE_SEND){
            ((SendMessViewHolder)holder).txtmess.setText(chatMessageList.get(position).mess);
            ((SendMessViewHolder)holder).txttime.setText(chatMessageList.get(position).datetime);
        }else {
            ((ReceivedMessViewHolder)holder).txtmess.setText(chatMessageList.get(position).mess);
            ((ReceivedMessViewHolder)holder).txttime.setText(chatMessageList.get(position).datetime);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessageList.get(position).sendid.equals(sendid)){
            return TYPE_SEND;
        }else {
            return TYPE_RECIEVED;
        }

    }

    class SendMessViewHolder extends RecyclerView.ViewHolder {
        TextView txtmess,txttime;
        public SendMessViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmess = itemView.findViewById(R.id.txt_mess1);
            txttime = itemView.findViewById(R.id.txt_time1);
        }
    }
    class ReceivedMessViewHolder extends RecyclerView.ViewHolder {
        TextView txtmess,txttime;
        public ReceivedMessViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmess = itemView.findViewById(R.id.txt_mess2);
            txttime = itemView.findViewById(R.id.txttime2);
        }
    }
}
