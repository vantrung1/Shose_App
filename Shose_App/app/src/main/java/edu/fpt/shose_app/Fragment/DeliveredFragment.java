package edu.fpt.shose_app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import edu.fpt.shose_app.Activity.ProductDetailActivity;
import edu.fpt.shose_app.Adapter.DeliveredAdapter;
import edu.fpt.shose_app.Adapter.TransportAtapter;
import edu.fpt.shose_app.Interface.ImageClickr;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.OderRequest;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeliveredFragment extends Fragment implements ImageClickr {
    ArrayList<Oder> oderArrayList;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    DeliveredAdapter deliveredAdapter;
    TextView txta;
    RecyclerView recy_delivered;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivered, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recy_delivered = view.findViewById(R.id.recy_delivered);
        txta = view.findViewById(R.id.txta);

        oderArrayList = null;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recy_delivered.setLayoutManager(layoutManager);

        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        get_oder(Utils.Users_Utils.getId(), 3);
        oderArrayList = new ArrayList<>();
        deliveredAdapter = new DeliveredAdapter(getActivity(), oderArrayList, this);
        recy_delivered.setAdapter(deliveredAdapter);
    }

    private void get_oder(int id, int status) {
        Call<OderRequest> objGetOder = apiInterface.getOder(id, status);
        // thực hiện gọi
        objGetOder.enqueue(new Callback<OderRequest>() {
            @Override
            public void onResponse(Call<OderRequest> call, Response<OderRequest> response) {
                if (response.isSuccessful()) {

                    OderRequest oderRequest = response.body();
//                    oderArrayList.clear();
                    oderArrayList = oderRequest.getData();
                    if (oderArrayList.size() == 0) {
                        txta.setVisibility(View.VISIBLE);
                    } else {
                        txta.setVisibility(View.INVISIBLE);
                    }

                    Log.d("zzzzzz", "onResponse: " + oderArrayList.size());
                    deliveredAdapter.setorderlist(oderArrayList);
                }
            }

            @Override
            public void onFailure(Call<OderRequest> call, Throwable t) {
                Log.d("ssssssssss", "onFailure: " + t.getLocalizedMessage());
            }

        });
    }

    public void getProduct(int id) {

        Call<List<Product>> objGetOder = apiInterface.getProduct(id);
        objGetOder.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Product products = response.body().get(0);
                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                    intent.putExtra("product", products);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onImageClick(int position) {
        getProduct(position);
    }

    @Override
    public void onResume() {
        get_oder(Utils.Users_Utils.getId(), 3);
        super.onResume();

    }
}