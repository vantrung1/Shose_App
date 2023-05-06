package edu.fpt.shose_app.Activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.fpt.shose_app.Adapter.HistoryAdapter;
import edu.fpt.shose_app.Adapter.ProductAdapter;
import edu.fpt.shose_app.Model.Product;
import edu.fpt.shose_app.R;
import edu.fpt.shose_app.Retrofit.ApiApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity implements HistoryAdapter.SearchViewListener{
    Toolbar toolbar;
    private RecyclerView rcv_seacrch;
    private ProductAdapter adapter;
    private HistoryAdapter historyAdapter;
    private List<Product> productList = new ArrayList<>();
    private static final String BASE_URL = "http://shoseapp.click/api/";

    private SearchView mSearchView;
    private ArrayAdapter<String> mHistoryAdapter;
    private ArrayList<String> mSearchHistoryList;
    private LinearLayout mSearchHistoryLayout;
    private ScrollView mSearchHistoryScrollView;
    private ListView mSearchResultsListView;
    private Context mContext;

    public static final int MAX_SEARCH_HISTORY = 20;
    public static final String SEARCH_HISTORY_PREFS_NAME = "SearchHistoryPrefs";
    public static final String SEARCH_HISTORY_PREFS_KEY = "SearchHistory";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Initialize search history list
        mSearchHistoryList = new ArrayList<>();

        initView();
        initActionBar();
        getProductList();
        HistorySearch();
//        loadSearchHistory();


    }
    //    private void HistorySearch(){
//        // Set up history adapter and list view
//        mHistoryAdapter = new HistoryAdapter(this, R.layout.search_history_item_layout, mSearchHistoryList, this); // Pass "this" as the listener parameter
//        mSearchResultsListView.setAdapter(mHistoryAdapter);
//        mSearchResultsListView.setVisibility(View.GONE);
//        // Set up history scroll view
//        mSearchHistoryScrollView.setVisibility(View.GONE);
//        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSearchResultsListView.setVisibility(View.VISIBLE);
//                mSearchHistoryScrollView.setVisibility(View.VISIBLE);
//                rcv_seacrch.setVisibility(View.GONE);
//            }
//        });
//
//        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                mSearchResultsListView.setVisibility(View.GONE);
//                mSearchHistoryScrollView.setVisibility(View.GONE);
//                rcv_seacrch.setVisibility(View.VISIBLE);
//                return false;
//            }
//        });
//    }
    private void initView() {
        rcv_seacrch = findViewById(R.id.rcv_search);
        toolbar = findViewById(R.id.toolbar);
        mSearchView = findViewById(R.id.search_view);
        mSearchHistoryLayout = findViewById(R.id.search_history_list_view);
        mSearchHistoryScrollView = findViewById(R.id.search_history_scrollview);
        mSearchResultsListView = findViewById(R.id.search_results_list_view);
        // Khởi tạo GridLayoutManager với spanCount là 2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcv_seacrch.setLayoutManager(layoutManager);
    }
    private void initActionBar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getProductList() {
        // Tạo một instance của Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Tạo một instance của API service
        ApiApp apiService = retrofit.create(ApiApp.class);
        // Lấy danh sách sản phẩm ban đầu
        Call<List<Product>> call = apiService.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    adapter = new ProductAdapter(SearchActivity.this, (ArrayList<Product>) productList);
                    rcv_seacrch.setAdapter(adapter);
                    // Bắt sự kiện cho SearchView
                    mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            adapter.getFilter().filter(query);
                            mSearchResultsListView.setVisibility(View.GONE);
                            mSearchHistoryScrollView.setVisibility(View.GONE);
                            rcv_seacrch.setVisibility(View.VISIBLE);
                            addSearchHistory(query);
                            // Hide keyboard and clear focus
                            mSearchView.clearFocus();
                            return true;

                            //vấn đề là thêm 2 kiểu khác nhau
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
//                            adapter.getFilter().filter(newText);
//                            mSearchResultsListView.setVisibility(View.GONE);
//                            mSearchHistoryScrollView.setVisibility(View.GONE);
//                            rcv_seacrch.setVisibility(View.VISIBLE);
                            // Lấy danh sách lịch sử tìm kiếm từ SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences(SEARCH_HISTORY_PREFS_NAME, MODE_PRIVATE);
                            Set<String> searchHistorySet = sharedPreferences.getStringSet(SEARCH_HISTORY_PREFS_KEY, new HashSet<String>());
                            mSearchHistoryList = new ArrayList<>(searchHistorySet);
                            // Lọc danh sách lịch sử tìm kiếm dựa trên newText
                            List<String> filteredHistoryList = new ArrayList<>();
                            for (String searchHistory : mSearchHistoryList) {
                                if (searchHistory.toLowerCase().startsWith(newText.toLowerCase())) {
                                    filteredHistoryList.add(searchHistory);
                                }
                            }
                            // Kiểm tra nếu danh sách lọc rỗng, thì thay bằng danh sách toàn bộ lịch sử tìm kiếm
                            if (filteredHistoryList.isEmpty()) {
                                filteredHistoryList = mSearchHistoryList;
                            }
                            // Cập nhật gợi ý tìm kiếm cho SearchView
                            Log.d("mSearchHistoryList", "onQueryTextChange: " + mSearchHistoryList);
                            mHistoryAdapter.clear();
                            mHistoryAdapter.addAll(filteredHistoryList);
                            mHistoryAdapter.notifyDataSetChanged();
                            return true;
                        }
                    });
                } else {
                    Log.e("TAG", "Request failed with code: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("TAG", "Request failed: " + t.getMessage());
            }
        });
    }
    private void HistorySearch(){
        // Set up history adapter and list view
        mHistoryAdapter = new HistoryAdapter(this, R.layout.search_history_item_layout, mSearchHistoryList, this); // Pass "this" as the listener parameter
        mSearchResultsListView.setAdapter(mHistoryAdapter); // khai báo cho mSearchResultsListView
        mSearchResultsListView.setVisibility(View.GONE);
        // Set up history scroll view
        mSearchHistoryScrollView.setVisibility(View.GONE);
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchResultsListView.setVisibility(View.VISIBLE);
                mSearchHistoryScrollView.setVisibility(View.VISIBLE);
                rcv_seacrch.setVisibility(View.GONE);
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mSearchResultsListView.setVisibility(View.GONE);
                mSearchHistoryScrollView.setVisibility(View.GONE);
                rcv_seacrch.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }
    //    private void SearchListHistory(){
//        // Set up search view
//        mSearchView.setQueryHint("Nhập từ khoá để tìm kiếm");
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Add query to search history list
//                if (!mSearchHistoryList.contains(query)) {
//                    addSearchHistory(query);
//                }
//                // Do search
////                performSearch(query);
//
//                // Collapse the search view
//                mSearchView.clearFocus();
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // Do nothing
////                performSearch(newText);
//                return false;
//            }
//        });
//        // Thiết lập hành động tìm kiếm khi người dùng thay đổi nội dung của SearchView
//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // Xử lý tìm kiếm với từ khoá là "query"
//                performSearch(query);
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // Thực hiện tìm kiếm tự động khi người dùng thay đổi nội dung của SearchView
//                performSearch(newText);
//                return false;
//            }
//        });
//    }
    @Override
    public SearchView getSearchView() {
        return mSearchView;
    }
    //    private void performSearch(String query) {
//        List<Product> results = new ArrayList<>();
//        // Loop through productList to find products that match query
//        for (Product product : productList) {
//            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
//                results.add(product);
//            }
//        }
//        // Update adapter with search results
//        historyAdapter.setProducts(results);
//        historyAdapter.notifyDataSetChanged();
//    }
    private void performSearch(String query) {
        // Clear the previous search results
        mSearchHistoryList.clear();

        // Check if the query is in the search history
        if (mSearchHistoryList.contains(query)) {
            // Remove the query from the search history
            mSearchHistoryList.remove(query);
            mHistoryAdapter.notifyDataSetChanged();
            saveSearchHistory();
        }

        // Add the query to the top of the search history
        mSearchHistoryList.add(0, query);
        mHistoryAdapter.notifyDataSetChanged();
        saveSearchHistory();

        // Update the search results list view
        mSearchResultsListView.setVisibility(View.VISIBLE);
        mSearchHistoryScrollView.setVisibility(View.GONE);
        mHistoryAdapter.notifyDataSetChanged();
    }

    private void addSearchHistory(String query) {
        // Check if the query already exists in the search history list
        for (int i = 0; i < mSearchHistoryList.size(); i++) {
            if (mSearchHistoryList.get(i).equals(query)) {
                // If the query already exists, remove it from the list
                mSearchHistoryList.remove(i);
                break;
            }
        }
        // Remove the oldest search history if the list exceeds the maximum limit
        if (mSearchHistoryList.size() >= MAX_SEARCH_HISTORY) {
            mSearchHistoryList.remove(mSearchHistoryList.size() - 1);
        }
        // Add the new search history to the beginning of the list
        mSearchHistoryList.add(0, query);
//        Collections.reverse(mSearchHistoryList);
        // Save search history to SharedPreferences
        saveSearchHistory();
        // Notify the adapter that the data has changed
        mHistoryAdapter.notifyDataSetChanged();
    }
    //    private void loadSearchHistory() {
//        SharedPreferences prefs = getSharedPreferences(SEARCH_HISTORY_PREFS_NAME, MODE_PRIVATE);
//        String searchHistoryString = prefs.getString(SEARCH_HISTORY_PREFS_KEY, null);
//        if (searchHistoryString != null) {
//            String[] searchHistoryArray = searchHistoryString.split(",");
//            Set<String> searchHistorySet = new HashSet<>(Arrays.asList(searchHistoryArray));
//            mSearchHistoryList.addAll(searchHistorySet);
//            mHistoryAdapter.notifyDataSetChanged();
//        }
//    }
    private void loadSearchHistory() {
//        SharedPreferences prefs = getSharedPreferences(SEARCH_HISTORY_PREFS_NAME, MODE_PRIVATE);
//        Set<String> searchHistorySet = prefs.getStringSet(SEARCH_HISTORY_PREFS_KEY, null);
        // Lấy danh sách lịch sử tìm kiếm từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCH_HISTORY_PREFS_NAME, MODE_PRIVATE);
        Set<String> searchHistorySet = sharedPreferences.getStringSet(SEARCH_HISTORY_PREFS_KEY, new HashSet<String>());
        List<String> searchHistoryList = new ArrayList<>(searchHistorySet);
        if (searchHistorySet != null) {
            mHistoryAdapter.addAll(searchHistoryList);
            mSearchHistoryList.addAll(searchHistoryList);
//            có vấn đề
            mHistoryAdapter.notifyDataSetChanged();
        }
    }
    private void saveSearchHistory() {
        SharedPreferences prefs = getSharedPreferences(SEARCH_HISTORY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> searchHistorySet = new HashSet<>(mSearchHistoryList);
        editor.putStringSet(SEARCH_HISTORY_PREFS_KEY, searchHistorySet);
        editor.apply();
    }
    public void removeSearchHistory(String query) {
        // Remove the search history from the list
        mSearchHistoryList.remove(query);
        // Remove the search history from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(SEARCH_HISTORY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(query);
        editor.apply();
        // Save the updated search history list
        saveSearchHistory();
        updateSearchHistoryList();
        // Notify the adapter that the data has changed
        mHistoryAdapter.notifyDataSetChanged();
    }
    private void updateSearchHistoryList() {
        SharedPreferences sharedPreferences = getSharedPreferences(SEARCH_HISTORY_PREFS_NAME, MODE_PRIVATE);
        Set<String> searchHistorySet = sharedPreferences.getStringSet(SEARCH_HISTORY_PREFS_KEY, new HashSet<String>());
        mSearchHistoryList = new ArrayList<>(searchHistorySet);
        mHistoryAdapter.clear();
        mHistoryAdapter.addAll(mSearchHistoryList);
        mHistoryAdapter.notifyDataSetChanged();
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.options_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

