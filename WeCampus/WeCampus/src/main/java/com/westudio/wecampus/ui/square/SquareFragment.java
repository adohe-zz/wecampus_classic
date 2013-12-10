package com.westudio.wecampus.ui.square;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.makeramen.RoundedImageView;
import com.westudio.wecampus.R;
import com.westudio.wecampus.data.model.ActivityCategory;
import com.westudio.wecampus.data.model.Advertisement;
import com.westudio.wecampus.net.WeCampusApi;
import com.westudio.wecampus.ui.activity.ActivityListActivity;
import com.westudio.wecampus.ui.base.BaseApplication;
import com.westudio.wecampus.ui.base.BaseFragment;
import com.westudio.wecampus.ui.base.WebBrowserActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    RoundedImageView banner1;
    RoundedImageView banner2;
    RoundedImageView banner3;

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

        banner1 = (RoundedImageView) view.findViewById(R.id.banner1);
        banner2 = (RoundedImageView) view.findViewById(R.id.banner2);
        banner3 = (RoundedImageView) view.findViewById(R.id.banner3);

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
        Drawable blankDrawable = new ColorDrawable(getResources().getColor(R.color.default_ad_color));

        banner1.setOnClickListener(adClickListenner);
        banner2.setOnClickListener(adClickListenner);
        banner3.setOnClickListener(adClickListenner);

        if (advertisementList != null && advertisementList.size() >= 3) {
            WeCampusApi.requestImage(advertisementList.get(2).getImage(),
                    WeCampusApi.getImageListener(banner1, null, blankDrawable));
            WeCampusApi.requestImage(advertisementList.get(1).getImage(),
                    WeCampusApi.getImageListener(banner2, null, blankDrawable));
            WeCampusApi.requestImage(advertisementList.get(0).getImage(),
                    WeCampusApi.getImageListener(banner3, null, blankDrawable));
        }
    }

    private View.OnClickListener adClickListenner = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String url = "";
            if (view.getId() == R.id.banner1 && advertisementList.size() > 0) {
                url = advertisementList.get(2).getUrl();
            } else if (view.getId() == R.id.banner2 && advertisementList.size() > 1) {
                url = advertisementList.get(1).getUrl();
            } else if (view.getId() == R.id.banner3 && advertisementList.size() > 2) {
                url = advertisementList.get(0).getUrl();
            }
            Intent intent = new Intent(getActivity(), WebBrowserActivity.class);
            intent.putExtra(WebBrowserActivity.EXTRA_URL, url);
            startActivity(intent);
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
            // 请求失败时填充缓存的category
            ActivityCategoryView categoryView;
            HashMap<String, String> colors = BaseApplication.getInstance().getCategoryColors();
            for (Map.Entry<String, String> entry : colors.entrySet()) {
                ActivityCategory category = new ActivityCategory();
                category.name = entry.getKey();
                category.color = entry.getValue();
                categoryView = new ActivityCategoryView(context);
                categoryView.setCategory(category);
                container.addView(categoryView);
            }
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
