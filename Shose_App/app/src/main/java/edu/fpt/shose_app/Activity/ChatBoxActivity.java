package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import edu.fpt.shose_app.Adapter.ChatAdapter;
import edu.fpt.shose_app.Model.ChatMessage;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Utils.Utils;

public class ChatBoxActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imgsend;
    EditText edMess;

    FirebaseFirestore db;

    ChatAdapter adapter;
    List<ChatMessage> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.ID_Received = "100";
        setContentView(R.layout.activity_chat_box);
        recyclerView = findViewById(R.id.recycleview_boxchat);
        imgsend = findViewById(R.id.img_btnchat);
        edMess = findViewById(R.id.ed_inputext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new ChatAdapter(getApplicationContext(),list,String.valueOf(Utils.Users_Utils.getId()));
        recyclerView.setAdapter(adapter);
        initControl();
        listenmess();
        insertUser();
    }
    private void insertUser(){
        HashMap<String, Object> user = new HashMap<>();
        user.put("id",Utils.Users_Utils.getId());
        user.put("name",Utils.Users_Utils.getName());
        db.collection("users").document(String.valueOf(Utils.Users_Utils.getId())).set(user);
    }
    private void initControl(){
        imgsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMesstoFire();
            }
        });
    }
    private void sendMesstoFire(){
        String str_mess = edMess.getText().toString().trim();
        if (TextUtils.isEmpty(str_mess)){

        }else {
            HashMap<String, Object> message = new HashMap<>();
            message.put(Utils.SEND_ID,String.valueOf(Utils.Users_Utils.getId()));
            message.put(Utils.RECEIVEDID_ID,Utils.ID_Received);
            message.put(Utils.MESS,str_mess);
            message.put(Utils.DATETIME,new Date());
            db.collection(Utils.PATH_CHAT).add(message);
            edMess.setText("");
        }
    }
    private void listenmess(){
        // tin nhan nguoi dung gui
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SEND_ID,String.valueOf(Utils.Users_Utils.getId()))
                .whereEqualTo(Utils.RECEIVEDID_ID,Utils.ID_Received)
                .addSnapshotListener(eventListener);
        // tin nhan admin gui
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SEND_ID,Utils.ID_Received)
                .whereEqualTo(Utils.RECEIVEDID_ID,String.valueOf(Utils.Users_Utils.getId()))
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener = (value, error)->{
        if(error !=null){
            return ;
        }
        if (value!=null){
            int count = list.size();
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType()==DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.sendid = documentChange.getDocument().getString(Utils.SEND_ID);
                    chatMessage.receivedid = documentChange.getDocument().getString(Utils.RECEIVEDID_ID);
                    chatMessage.mess = documentChange.getDocument().getString(Utils.MESS);
                    chatMessage.dateObj = documentChange.getDocument().getDate(Utils.DATETIME);
                    chatMessage.datetime = format_date(documentChange.getDocument().getDate(Utils.DATETIME));
                    list.add(chatMessage);
                }
            }
            Collections.sort(list,(obj1, obj2)-> obj1.dateObj.compareTo(obj2.dateObj));
            if(count == 0){
                adapter.notifyDataSetChanged();
            }else {
                adapter.notifyItemRangeInserted(list.size(),list.size());
                recyclerView.smoothScrollToPosition(list.size()-1);
            }
        }
    };

    private String format_date(Date date){
        return new SimpleDateFormat("dd MMMM, yyyy - H:mm:ss", Locale.getDefault()).format(date);
    }

}