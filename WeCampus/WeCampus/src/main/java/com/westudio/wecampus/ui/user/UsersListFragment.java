package com.westudio.wecampus.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.util.PinYin;
import com.woozzu.android.widget.IndexableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by jam on 13-9-19.
 *
 * Fragment display user list
 */
public class UsersListFragment extends BaseFragment {

    private IndexableListView mUserList;
    private UserAdapter mAdapter;

    private Activity activity;

    public static UsersListFragment newInstance() {
        UsersListFragment f = new UsersListFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Mock user list
        String name = "013*is傻9AB阿9D&JK王MN吴P罗RS杨WX蔡Z刘周%方何简#留坚";
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < 36; i++) {
            User u = new User();
            u.Name = name.charAt(i >= name.length() ? name.length() - 1 : i) + "aldjfaldkj";
            users.add(u);
        }

        mUserList = (IndexableListView) view.findViewById(R.id.user_list);
        Collections.sort(users, new Comparator<User>() {

            @Override
            public int compare(User user, User user2) {
                return PinYin.getPinYin(user.Name).compareTo(PinYin.getPinYin(user2.Name));
            }
        });

        mAdapter = new UserAdapter(getActivity(), users);
        mUserList.setAdapter(mAdapter);
        mUserList.setFastScrollEnabled(true);
        mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(activity, UserProfileActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}
