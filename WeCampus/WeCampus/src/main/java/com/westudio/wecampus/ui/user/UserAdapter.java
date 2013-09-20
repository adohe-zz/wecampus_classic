package com.westudio.wecampus.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.woozzu.android.util.StringMatcher;

import java.util.List;

/**
 * Created by martian on 13-9-19.
 */
public class UserAdapter extends ArrayAdapter<User> implements SectionIndexer {

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static class ViewHolder {
        public TextView name;
    }

    public UserAdapter(Context context, List<User> objects) {
        super(context, R.layout.row_user_list, R.id.user_name, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_user_list, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.user_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(getItem(position).Name);

        return convertView;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(getItem(j).Name.charAt(0)),
                                String.valueOf(k))) {
                            return j;
                        }
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(getItem(j).Name.charAt(0)),
                            String.valueOf(mSections.charAt(i)))) {
                        return j;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }
}
