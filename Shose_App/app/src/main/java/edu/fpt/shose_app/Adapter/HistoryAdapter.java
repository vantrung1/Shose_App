package edu.fpt.shose_app.Adapter;
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
import edu.fpt.shose_app.Model.Product;
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
                ((SearchActivity) mContext).removeSearchHistory(searchQuery);
                mListener.getSearchView().setIconified(false);
                mListener.getSearchView().setQuery("", false);
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
    @Override
    public String getItem(int position) {
        return mSearchHistoryList.get(position);
    }

}