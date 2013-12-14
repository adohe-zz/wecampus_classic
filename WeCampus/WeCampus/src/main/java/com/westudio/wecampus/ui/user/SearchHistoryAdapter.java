package com.westudio.wecampus.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.SearchHistory;

import java.util.List;

/**
 * Created by martian on 13-12-13.
 */
public class SearchHistoryAdapter extends BaseAdapter{

    private List<SearchHistory> list;
    private LayoutInflater inflater;


    public SearchHistoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView keywords;
        if (view == null) {
            view = inflater.inflate(R.layout.row_search_history, viewGroup, false);
        }

        keywords = (TextView) view.findViewById(R.id.tv_keywords);
        keywords.setText(list.get(i).keywords);

        return view;
    }
}
