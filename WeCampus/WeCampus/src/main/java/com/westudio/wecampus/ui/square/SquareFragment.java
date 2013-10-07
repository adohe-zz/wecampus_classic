package com.westudio.wecampus.ui.square;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;
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


        // set banners
        NetworkImageView banner1 = (NetworkImageView) view.findViewById(R.id.banner1);
        NetworkImageView banner2 = (NetworkImageView) view.findViewById(R.id.banner2);
        NetworkImageView banner3 = (NetworkImageView) view.findViewById(R.id.banner3);
        ImageLoader imageLoader = WeCampusApi.getImageLoader();



        banner1.setImageUrl("http://img3.douban.com/view/photo/photo/public/p1534863856.jpg", imageLoader);
        banner2.setImageUrl("http://img3.douban.com/view/photo/photo/public/p2147894217.jpg", imageLoader);
        banner3.setImageUrl("http://img3.douban.com/view/photo/photo/public/p1954976120.jpg", imageLoader);

        return view;
    }
}
