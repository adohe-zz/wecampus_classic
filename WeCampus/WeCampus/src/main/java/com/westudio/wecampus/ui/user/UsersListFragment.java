package com.westudio.wecampus.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.User;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.woozzu.android.widget.IndexableListView;

import java.util.ArrayList;

/**
 * Created by jam on 13-9-19.
 *
 * Fragment display user list
 */
public class UsersListFragment extends BaseFragment {

    private IndexableListView mUserList;
    private UserAdapter mAdapter;

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
        String name = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < 36; i++) {
            User u = new User();
            u.Name = name.charAt(i >= name.length() ? name.length() - 1 : i) + "aldjfaldkj";
            users.add(u);
        }

        mUserList = (IndexableListView) view.findViewById(R.id.user_list);
        mAdapter = new UserAdapter(getActivity(), users);
        mUserList.setAdapter(mAdapter);
        mUserList.setFastScrollEnabled(true);

        return view;
    }
}
