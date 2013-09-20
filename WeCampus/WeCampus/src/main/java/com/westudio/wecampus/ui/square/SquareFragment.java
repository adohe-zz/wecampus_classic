package com.westudio.wecampus.ui.square;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.westudio.wecampus.R;
import com.westudio.wecampus.ui.base.BaseFragment;

/**
 * Created by jam on 13-9-20.
 */
public class SquareFragment extends BaseFragment {

    public static SquareFragment newInstance() {
        SquareFragment f = new SquareFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);

        return view;
    }
}
