package edu.fpt.shose_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.Model.RatingModel;
import edu.fpt.shose_app.R;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyviewHolder> {
    List<RatingModel.rating> ratingList;
    Context context;

    public RatingAdapter(List<RatingModel.rating> ratingList, Context context) {
        this.ratingList = ratingList;
        this.context = context;
    }

    public void setRatingList(List<RatingModel.rating> ratingList1) {
        ratingList =new ArrayList<>();
        this.ratingList = ratingList1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RatingAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_item, parent, false);
        return new RatingAdapter.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.MyviewHolder holder, int position) {
        Glide.with(context).load(ratingList.get(position).getAvatar()).placeholder(R.drawable.image_profile).into(holder.circleImageView);
        holder.textViewname.setText(ratingList.get(position).getUsername());
        holder.textViewcreateat.setText(ratingList.get(position).getCreated_at());
        holder.textViewcontent.setText(ratingList.get(position).getContent());
        Gson gson =new Gson();
        ArrayList<Image> list =new ArrayList<>();
        List<Map<String, Image>> listMap = gson.fromJson(ratingList.get(position).getImage(), new TypeToken<List<Map<String, Image>>>() {}.getType());
        for (Map<String, Image> map : listMap) {
                        // Truy cập và lấy giá trị từ các cặp key-value trong map
            for (Map.Entry<String, Image> entry : map.entrySet()) {

                Image value = entry.getValue();
                list.add(value);
                        }
                    }
        holder.recyclerView.setAdapter(new ImageFeedbackAdapter2(context,list));
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.ratingBar.setRating(Float.parseFloat(ratingList.get(position).getStar()));
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView textViewname, textViewcontent,textViewcreateat;
        RatingBar ratingBar;
        RecyclerView recyclerView;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.ratingavatar);
            textViewname = itemView.findViewById(R.id.ratingName);
            textViewcontent = itemView.findViewById(R.id.ratingcontent);
            textViewcreateat = itemView.findViewById(R.id.textViewcreateat);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            recyclerView = itemView.findViewById(R.id.recyImageRating);
        }
    }
}
