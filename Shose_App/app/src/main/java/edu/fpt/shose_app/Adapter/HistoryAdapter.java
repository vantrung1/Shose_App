package edu.fpt.shose_app.Adapter;//package com.example.recyclerview_notify;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class HistoryAdapter extends ArrayAdapter<String> {
//
//    private ArrayList<String> mItems;
//    private Context mContext;
//
//    public HistoryAdapter(Context context, int resource, ArrayList<String> items) {
//        super(context, resource, items);
//        mContext = context;
//        mItems = items;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
//        }
//
//        String item = mItems.get(position);
//
//        TextView textView = convertView.findViewById(android.R.id.text1);
//        textView.setText(item);
//
//        return convertView;
//    }
//
//}
//



////ccode hoàn thiện v2
//package com.example.recyclerview_notify;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class HistoryAdapter extends ArrayAdapter<String> {
//
//    private ArrayList<String> mItems;
//    private Context mContext;
//    public SearchActivity mSearchActivity;
//
//    public HistoryAdapter(SearchActivity searchActivity, ArrayList<String> items) {
//        super(searchActivity, R.layout.history_item_layout, items);
//        mSearchActivity = searchActivity;
//        mContext = searchActivity.getApplicationContext();
//        mItems = items;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext())
//                    .inflate(R.layout.history_item_layout, parent, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.historyText = convertView.findViewById(R.id.history_item_text);
//            viewHolder.deleteButton = convertView.findViewById(R.id.history_item_delete);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        String historyItem = getItem(position);
//
//        viewHolder.historyText.setText(historyItem);
//        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mItems.remove(position);
//                notifyDataSetChanged();
//                mSearchActivity.deleteSearchHistory();
//                mSearchActivity.saveSearchHistory(mItems);
//            }
//        });
//        viewHolder.historyText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSearchActivity.mSearchView.setQuery(historyItem, true);
//            }
//        });
//
//
//        return convertView;
//    }
//
//    private static class ViewHolder {
//        TextView historyText;
//        Button deleteButton;
//    }
//}



////ccode hoàn thiện v3
//package com.example.recyclerview_notify;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.SearchView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HistoryAdapter extends ArrayAdapter<String> {
//
//    private Context mContext;
//    private int mResource;
//    private ArrayList<String> mSearchHistoryList;
//    private OnHistoryItemClickListener mListener;
//
//    public HistoryAdapter(Context context, int resource, ArrayList<String> searchHistoryList) {
//        super(context, resource, searchHistoryList);
//        mContext = context;
//        mResource = resource;
//        mSearchHistoryList = searchHistoryList;
//    }
//
//    public void setOnHistoryItemClickListener(OnHistoryItemClickListener listener) {
//        mListener = listener;
//    }
//    public interface OnHistoryItemClickListener {
//        void onHistoryItemClick(String query);
//    }
//
//
//    @NonNull
//    @Override
//    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View view = inflater.inflate(mResource, parent, false);
//
//        TextView textView = view.findViewById(R.id.history_item_text);
//        textView.setText(mSearchHistoryList.get(position));
//
//        ImageView deleteIcon = view.findViewById(R.id.history_item_delete);
//        deleteIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSearchHistoryList.remove(position);
//                notifyDataSetChanged();
//            }
//        });
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mListener != null) {
//                    mListener.onHistoryItemClick(mSearchHistoryList.get(position));
//                }
//            }
//        });
//
//        return view;
//    }
//}

////code hoan thiẹn
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import androidx.appcompat.widget.SearchView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class HistoryAdapter extends ArrayAdapter<String> {
//    private ArrayList<String> mSearchHistoryList;
//    private SearchViewListener mListener;
//
//    public interface SearchViewListener {
//        androidx.appcompat.widget.SearchView getSearchView();
//    }
//    public HistoryAdapter(Context context, int resource, ArrayList<String> searchHistoryList, SearchViewListener listener) {
//        super(context, resource, searchHistoryList);
//        mSearchHistoryList = searchHistoryList;
//        mListener = listener;
//    }
//
//    @Override
//    public int getCount() {
//        return mSearchHistoryList.size();
//    }
//
//    @Override
//    public String getItem(int position) {
//        return mSearchHistoryList.get(position);
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_history_item_layout, parent, false);
//        }
//
//        TextView textView = convertView.findViewById(R.id.history_item_text);
//        textView.setText(getItem(position));
//        // Bắt sự kiện click vào phần tử lịch sử
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchView searchView = mListener.getSearchView();
//                if (searchView != null) {
//                    String query = getItem(position);
//                    searchView.setQuery(query, true);
//                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
//                }
//            }
//
//        });
//
//        ImageView deleteIcon = convertView.findViewById(R.id.history_item_delete);
//        deleteIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSearchHistoryList.remove(position);
//                notifyDataSetChanged();
//            }
//        });
//
//
//
//
//        return convertView;
//    }
//
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import java.util.List;

import edu.fpt.shose_app.Activity.SearchActivity;
import edu.fpt.shose_app.R;

public class HistoryAdapter extends ArrayAdapter<String> {
    private final Context mContext;
    private final int mLayoutResourceId;
    private final List<String> mSearchHistoryList;
    private final SearchViewListener mListener;

    public HistoryAdapter(Context context, int layoutResourceId, List<String> searchHistoryList, SearchViewListener listener) {
        super(context, layoutResourceId, searchHistoryList);
        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mSearchHistoryList = searchHistoryList;
        this.mListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HistoryItemHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new HistoryItemHolder();
            holder.searchText = row.findViewById(R.id.history_item_text);
            holder.deleteButton = row.findViewById(R.id.history_item_delete);

            row.setTag(holder);
        } else {
            holder = (HistoryItemHolder) row.getTag();
        }

        final String searchQuery = getItem(position);

        holder.searchText.setText(searchQuery);
        holder.searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.getSearchView().setQuery(searchQuery, true);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((SearchActivity) mContext).removeSearchHistory(searchQuery);
                        mListener.getSearchView().setIconified(false);
                        mListener.getSearchView().setQuery("", false);
                    }
                });

            }
        });

        return row;
    }

    static class HistoryItemHolder {
        TextView searchText;
        ImageView deleteButton;
    }

    public interface SearchViewListener {
        SearchView getSearchView();
    }
    @Override
    public int getCount() {
        return mSearchHistoryList.size();
    }

}
