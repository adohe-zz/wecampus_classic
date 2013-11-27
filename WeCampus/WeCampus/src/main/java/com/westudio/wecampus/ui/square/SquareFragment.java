package com.westudio.wecampus.ui.square;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityCategory;
import com.westudio.wecampus.data.model.Advertisement;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.base.BaseFragment;

import java.util.List;

/**
 * Created by Martian on 13-9-20.
 */
public class SquareFragment extends BaseFragment implements View.OnClickListener,
        Response.Listener<Advertisement.AdResultData>, Response.ErrorListener {
    private EditText mSerachBar;
    private List<Advertisement> advertisementList;
    private ImageLoader imageLoader;
    private View view;
    private CategoryHandler categoryHandler;

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
        view = inflater.inflate(R.layout.fragment_square, container, false);


        imageLoader = WeCampusApi.getImageLoader();
        // set banners
        WeCampusApi.getAdvertisement(this, this, this);

        mSerachBar = (EditText) view.findViewById(R.id.search_bar);
        mSerachBar.setOnClickListener(this);

        categoryHandler = new CategoryHandler(view, getActivity());
        categoryHandler.fetchCategory();
        return view;
    }

    @Override
    public void onDetach() {
        WeCampusApi.cancelRequest(this);
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_bar: {
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(Advertisement.AdResultData adResultData) {
        advertisementList = adResultData.objects;
        setBanners();
    }

    private void setBanners() {
        NetworkImageView banner1 = (NetworkImageView) view.findViewById(R.id.banner1);
        NetworkImageView banner2 = (NetworkImageView) view.findViewById(R.id.banner2);
        NetworkImageView banner3 = (NetworkImageView) view.findViewById(R.id.banner3);
        banner1.setOnClickListener(adClickListenner);
        banner2.setOnClickListener(adClickListenner);
        banner3.setOnClickListener(adClickListenner);

        if (advertisementList != null && advertisementList.size() >= 3) {
            banner1.setImageUrl(advertisementList.get(0).getImage(), imageLoader);
            banner2.setImageUrl(advertisementList.get(1).getImage(), imageLoader);
            banner3.setImageUrl(advertisementList.get(2).getImage(), imageLoader);
        }
    }

    private View.OnClickListener adClickListenner = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String url = "";
            if (view.getId() == R.id.banner1 && advertisementList.size() > 0) {
                url = advertisementList.get(0).getUrl();
            } else if (view.getId() == R.id.banner2 && advertisementList.size() > 1) {
                url = advertisementList.get(1).getUrl();
            } else if (view.getId() == R.id.banner3 && advertisementList.size() > 2) {
                url = advertisementList.get(2).getUrl();
            }
            Intent intent = new Intent();
            intent.setData(Uri.parse(url));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
            }
        }
    };

    private class CategoryHandler implements Response.Listener<ActivityCategory.CategoryRequestData>,
            Response.ErrorListener {
        private LinearLayout container;
        private Context context;

        public CategoryHandler(View rootView, Context context) {
            this.context = context;
            container = (LinearLayout) rootView.findViewById(R.id.banner_and_catgories);
        }

        public void fetchCategory() {
            WeCampusApi.getActivityCategory(SquareFragment.this, this, this);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
        }

        @Override
        public void onResponse(ActivityCategory.CategoryRequestData data) {
            ActivityCategoryView categoryView;
            for (ActivityCategory category : data.getObjects()) {
                categoryView = new ActivityCategoryView(context);
                categoryView.setCategory(category);
                container.addView(categoryView);
            }

        }
    }
}
