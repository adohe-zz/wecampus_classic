package com.westudio.wecampus.ui.friends;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.util.Constants;
import com.westudio.wecampus.util.PinYin;

/**
 * Created by martian on 13-9-19.
 */
public class FriendsAdapter extends CursorAdapter implements SectionIndexer {

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private LayoutInflater mLayoutInflater;
    private ListView mListView;

    private Drawable defaultDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));
    private Bitmap defaultMaleDrawable;
    private Bitmap defaultFemaleDrawable;

    public FriendsAdapter(Context context, ListView listView) {
        super(context, null , false);
        mLayoutInflater = LayoutInflater.from(context);
        mListView = listView;
        defaultMaleDrawable = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_male);
        defaultFemaleDrawable = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_female);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mLayoutInflater.inflate(R.layout.row_user_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = getHolder(view);
        if(holder.imageRequest != null) {
            holder.imageRequest.cancelRequest();
        }

        view.setEnabled(!mListView.isItemChecked(cursor.getPosition()
                + mListView.getHeaderViewsCount()));

        User user = User.fromCursor(cursor);
        holder.nickname.setText(user.nickname);
        if(Constants.IMAGE_NOT_FOUND.equals(user.avatar)) {
            if(Constants.MALE.equals(user.gender)) {
                holder.avatar.setImageBitmap(defaultMaleDrawable);
            } else {
                holder.avatar.setImageBitmap(defaultFemaleDrawable);
            }
        } else {
            holder.imageRequest = WeCampusApi.requestImage(user.avatar,
                    WeCampusApi.getImageListener(holder.avatar, defaultDrawable, defaultDrawable));
        }
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
                    // not English nor Chinese
                    char c = getItem(j).nickname.charAt(0);
                    if ((c < 19968 && c > 171941) && (c < 'a' && c > 'z')
                            && (c < 'A' && c > 'Z')) {
                        return j;
                    }
                } else {
                    if (PinYin.match(String.valueOf(getItem(j).nickname.charAt(0)),
                            mSections.charAt(i))) {
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

    @Override
    public User getItem(int position) {
        return User.fromCursor(mCursor);
    }

    private ViewHolder getHolder(final View view) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();

        if(viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        return viewHolder;
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView nickname;
        ImageLoader.ImageContainer imageRequest;

        public ViewHolder(View view) {
            avatar = (ImageView) view.findViewById(R.id.user_avatar);
            nickname = (TextView) view.findViewById(R.id.user_name);
        }
    }
}
