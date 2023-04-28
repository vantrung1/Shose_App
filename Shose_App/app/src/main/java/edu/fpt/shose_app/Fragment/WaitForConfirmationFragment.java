package edu.fpt.shose_app.Fragment;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import edu.fpt.shose_app.Activity.MyCartActivity;
import edu.fpt.shose_app.Adapter.MyCartAdapter;
import edu.fpt.shose_app.Adapter.OderAdapter;
import edu.fpt.shose_app.Model.Oder;
import edu.fpt.shose_app.Model.OderRequest;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.Model.ProductRequest;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import edu.fpt.shose_app.Utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WaitForConfirmationFragment extends Fragment {
    ArrayList<Oder> oderArrayList;
    Retrofit retrofit;
    Gson gson;
    ApiApp apiInterface;
    OderAdapter oderAdapter;
    TextView txta;
    RecyclerView recy_wait_for;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wait_for_confirmation, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recy_wait_for = view.findViewById(R.id.recy_wait_for);
        txta = view.findViewById(R.id.txta);

        oderArrayList = null;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recy_wait_for.setLayoutManager(layoutManager);

        gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiApp.class);
        get_oder(Utils.Users_Utils.getId(),1);
        oderArrayList = new ArrayList<>();
        oderAdapter = new OderAdapter(getActivity(), oderArrayList);
        recy_wait_for.setAdapter(oderAdapter);
    }

    private void get_oder(int id, int status) {
        Call<OderRequest> objGetOder = apiInterface.getOder(id,status);
        // thực hiện gọi
        objGetOder.enqueue(new Callback<OderRequest>() {
            @Override
            public void onResponse(Call<OderRequest> call, Response<OderRequest> response) {
                if (response.isSuccessful()) {
                    OderRequest oderRequest = response.body();
//                    oderArrayList.clear();
                    oderArrayList = oderRequest.getData();
//                    if (oderArrayList.size() == 0) {
//                        txta.setVisibility(View.VISIBLE);
//                    } else {
//                        txta.setVisibility(View.INVISIBLE);
//                    }

                    Log.d("zzzzzz", "onResponse: " + oderArrayList.size());
                    oderAdapter.setorderlist(oderArrayList);

                }
            }

            @Override
            public void onFailure(Call<OderRequest> call, Throwable t) {
                Log.d("ssssssssss", "onFailure: " + t.getLocalizedMessage());
            }

        });
    }

}