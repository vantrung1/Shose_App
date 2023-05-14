package edu.fpt.shose_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.fpt.shose_app.Adapter.RatingAdapter;
import edu.fpt.shose_app.Adapter.starAtapter;
import edu.fpt.shose_app.Model.Image;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.RatingModel;
import edu.fpt.shose_app.Model.starmodel;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import edu.fpt.shose_app.dialogModel.EventBus.BrandEvent;
import edu.fpt.shose_app.dialogModel.EventBus.starEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RatingsActivity extends AppCompatActivity {
    RecyclerView recyclerView,recyclerViewRating;
    Toolbar toolbar;
    List<starmodel> list =new ArrayList<>();
    starAtapter atapter;
    RatingAdapter ratingAdapter;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    private RatingModel ratingModel ;
    String idProdut;
    TextView txtcenter, textViewnn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ratings);
        ratingModel = (RatingModel) getIntent().getSerializableExtra("rating");
        list.add(new starmodel(0,"Tất Cả"));

        list.add(new starmodel(5,"Đánh giá 5*"));
        list.add(new starmodel(4,"Đánh giá 4*"));
        list.add(new starmodel(3,"Đánh giá 3*"));
        list.add(new starmodel(2,"Đánh giá 2*"));
        list.add(new starmodel(1,"Đánh giá 1*"));
        atapter = new starAtapter(getApplicationContext(),list);
        ratingAdapter = new RatingAdapter(ratingModel.getData(),getApplicationContext());
        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiApp.class);
        initUI();
        initAction();
    }
    private void initAction() {

    }

    private void initUI() {
        recyclerView = findViewById(R.id.recs);
        recyclerViewRating = findViewById(R.id.recs2);
        txtcenter = findViewById(R.id.ttttt);
        textViewnn = findViewById(R.id.textnn);
        recyclerView.setAdapter(atapter);
        recyclerViewRating.setAdapter(ratingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclerViewRating.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));
        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đánh Giá");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbar.setNavigationOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        if(ratingModel.getData().size()>0){
            textViewnn.setVisibility(View.VISIBLE);
            txtcenter.setVisibility(View.INVISIBLE);
        }
    }
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventStar(starEvent event) {
        if (event != null) {
            Log.d("TAG", "eventStar: "+atapter.getSelected());
            int star = atapter.getSelected();
            if(star == 0){
                ratingAdapter.setRatingList(ratingModel.getData());
            }else {
                List<RatingModel.rating> ratingListnew = new ArrayList<>();
                for(RatingModel.rating rating : ratingModel.getData()){
                    if(rating.getStar().equals(star+"")){
                        ratingListnew.add(rating);
                    }
                }
                ratingAdapter.setRatingList(ratingListnew);
            }
        }

    }


}